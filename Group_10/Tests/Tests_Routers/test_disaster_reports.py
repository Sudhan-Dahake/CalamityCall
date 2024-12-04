import sys
import os
import pytest
from fastapi.testclient import TestClient
from datetime import datetime, timezone

# Adjust the path to import your main FastAPI app
import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "../../..")))


# from Api.main import app  # Import your FastAPI app

from Group_10.Api.main import app

from Api.routers.disaster_reports import DisasterReport, Location, Event

client = TestClient(app)

# Fixture for a sample disaster report
@pytest.fixture
def sample_disaster_report():
    return {
        "report_id": "test_report_1",
        "user_id": 1,
        "created_at": datetime.now(timezone.utc).isoformat(),
        "location": {
            "latitude": 40.7128,
            "longitude": -74.0060,
            "address": "New York, NY"
        },
        "event": {
            "type": "Hurricane",
            "severity": "High",
            "description": "Category 4 hurricane approaching"
        }
    }

# Test creating a disaster report
def test_create_disaster_report(sample_disaster_report):
    response = client.post("/report/", json=sample_disaster_report)
    assert response.status_code == 201
    assert "message" in response.json()
    assert "reportJson" in response.json()

# Test getting a specific disaster report
def test_get_disaster_report(sample_disaster_report):
    # First, create a report
    create_response = client.post("/report/", json=sample_disaster_report)
    assert create_response.status_code == 201

    # Then, try to retrieve it
    report_id = sample_disaster_report["report_id"]
    response = client.get(f"/report/{report_id}")
    assert response.status_code == 200
    assert response.json()["report_id"] == report_id

# Test getting all disaster reports
def test_get_all_disaster_reports():
    response = client.get("/report/")
    assert response.status_code == 200
    assert "disaster_reports" in response.json()

# Test updating a disaster report
def test_update_disaster_report(sample_disaster_report):
    # First, create a report
    create_response = client.post("/report/", json=sample_disaster_report)
    assert create_response.status_code == 201

    # Update the report
    report_id = sample_disaster_report["report_id"]
    updated_data = sample_disaster_report.copy()
    updated_data["event"]["severity"] = "Medium"
    response = client.put(f"/report/{report_id}", json=updated_data)
    assert response.status_code == 200
    assert "message" in response.json()

# Test deleting a disaster report
def test_delete_disaster_report(sample_disaster_report):
    # First, create a report
    create_response = client.post("/report/", json=sample_disaster_report)
    assert create_response.status_code == 201

    # Delete the report
    report_id = sample_disaster_report["report_id"]
    response = client.delete(f"/report/{report_id}")
    assert response.status_code == 200
    assert "message" in response.json()

# Test creating a report with invalid data
def test_create_invalid_disaster_report():
    invalid_report = {
        "report_id": "invalid_report",
        "user_id": "not_an_integer",  # This should be an integer
        "created_at": "invalid_date",
        "location": {
            "latitude": "not_a_float",
            "longitude": -74.0060,
            "address": ""  # Empty address
        },
        "event": {
            "type": "",  # Empty type
            "severity": "Unknown",  # Invalid severity
            "description": "Test description"
        }
    }
    response = client.post("/report/", json=invalid_report)
    assert response.status_code == 422  # Unprocessable Entity

# Test getting a non-existent report
def test_get_nonexistent_report():
    response = client.get("/report/nonexistent_id")
    assert response.status_code == 404

# Edge test cases
def test_create_report_with_extreme_coordinates():
    extreme_report = {
        "report_id": "extreme_coords",
        "user_id": 1,
        "created_at": datetime.now(timezone.utc).isoformat(),
        "location": {
            "latitude": 90,  # North Pole
            "longitude": 180,  # International Date Line
            "address": "Extreme Location"
        },
        "event": {
            "type": "Extreme Weather",
            "severity": "Very High",
            "description": "Testing extreme coordinates"
        }
    }
    response = client.post("/report/", json=extreme_report)
    assert response.status_code == 201

def test_create_report_with_very_long_description():
    long_description = "A" * 10000  # Very long description
    long_desc_report = {
        "report_id": "long_desc",
        "user_id": 1,
        "created_at": datetime.now(timezone.utc).isoformat(),
        "location": {
            "latitude": 0,
            "longitude": 0,
            "address": "Zero Island"
        },
        "event": {
            "type": "Test",
            "severity": "Low",
            "description": long_description
        }
    }
    response = client.post("/report/", json=long_desc_report)
    assert response.status_code == 201

def test_update_nonexistent_report():
    nonexistent_report = {
        "report_id": "nonexistent",
        "user_id": 1,
        "created_at": datetime.now(timezone.utc).isoformat(),
        "location": {
            "latitude": 0,
            "longitude": 0,
            "address": "Nowhere"
        },
        "event": {
            "type": "Test",
            "severity": "Low",
            "description": "This report doesn't exist"
        }
    }
    response = client.put("/report/nonexistent", json=nonexistent_report)
    assert response.status_code == 404

def test_delete_already_deleted_report(sample_disaster_report):
    # First, create and delete a report
    create_response = client.post("/report/", json=sample_disaster_report)
    assert create_response.status_code == 201
    report_id = sample_disaster_report["report_id"]
    delete_response = client.delete(f"/report/{report_id}")
    assert delete_response.status_code == 200

    # Try to delete the same report again
    second_delete_response = client.delete(f"/report/{report_id}")
    assert second_delete_response.status_code == 404