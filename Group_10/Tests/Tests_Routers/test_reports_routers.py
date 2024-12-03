import sys
import os
import pytest
from fastapi.testclient import TestClient
from datetime import datetime, timezone

sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

from Api.main import app
from Api.schemas.reports import DisasterReportCreate

client = TestClient(app)

@pytest.fixture
def sample_disaster_report():
    return {
        "report_id": "test_report_1",
        "user_id": 1,
        "timestamp": datetime.now().isoformat(),
        "latitude": 40.7128,
        "longitude": -74.0060,
        "address": "New York, NY",
        "weather_event_type": "Hurricane",
        "weather_event_severity": "High",
        "weather_event_description": "Category 4 hurricane approaching"
    }

def test_create_disaster_report(sample_disaster_report):
    response = client.post("/disaster-reports/", json=sample_disaster_report)
    assert response.status_code == 200
    assert "report_id" in response.json()

def test_create_disaster_report_invalid_data():
    invalid_report = {
        "report_id": "invalid_report",
        "user_id": "not_an_integer",
        "timestamp": "invalid_date",
        "latitude": "not_a_float",
        "longitude": -74.0060,
        "address": "",
        "weather_event_type": "",
        "weather_event_severity": "Unknown",
        "weather_event_description": "Test description"
    }
    response = client.post("/disaster-reports/", json=invalid_report)
    assert response.status_code == 422

# Edge Test Cases
def test_create_disaster_report_with_future_timestamp():
    future_report = {
        "report_id": "future_report",
        "user_id": 1,
        "timestamp": (datetime.now(timezone.utc) + timedelta(days=365)).isoformat(),
        "latitude": 0,
        "longitude": 0,
        "address": "Future City",
        "weather_event_type": "Future Storm",
        "weather_event_severity": "Unknown",
        "weather_event_description": "This event hasn't happened yet"
    }
    response = client.post("/disaster-reports/", json=future_report)
    assert response.status_code == 422  # Assuming future timestamps are not allowed

def test_create_disaster_report_with_invalid_coordinates():
    invalid_coords_report = {
        "report_id": "invalid_coords",
        "user_id": 1,
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "latitude": 91,  # Invalid latitude
        "longitude": 181,  # Invalid longitude
        "address": "Invalid Location",
        "weather_event_type": "Test",
        "weather_event_severity": "Low",
        "weather_event_description": "Testing invalid coordinates"
    }
    response = client.post("/disaster-reports/", json=invalid_coords_report)
    assert response.status_code == 422

def test_create_disaster_report_with_unicode_characters():
    unicode_report = {
        "report_id": "unicode_report",
        "user_id": 1,
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "latitude": 0,
        "longitude": 0,
        "address": "Unicode City 🌍",
        "weather_event_type": "Strange Weather ☀️🌧️",
        "weather_event_severity": "Medium",
        "weather_event_description": "This report contains unicode characters: 你好世界"
    }
    response = client.post("/disaster-reports/", json=unicode_report)
    assert response.status_code == 200