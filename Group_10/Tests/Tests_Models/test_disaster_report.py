import unittest
from unittest.mock import patch, MagicMock

from Group_10 import DisasterReportsModel

class TestDisasterReportsModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.disaster_reports.create_client')
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.disaster_model = DisasterReportsModel()

    def test_create_report_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{"report_id": 1}])
        response = self.disaster_model.CreateReport(1, 1, "2024-12-01T12:00:00Z", 40.7128, -74.0060, "NYC", "Flood", "Severe", "Severe flooding in NYC")
        self.assertIsNotNone(response)
        self.assertEqual(response['report_id'], 1)

    def test_create_report_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.disaster_model.CreateReport(1, 1, "2024-12-01T12:00:00Z", 40.7128, -74.0060, "NYC", "Flood", "Severe", "Severe flooding in NYC")
        self.assertIsNone(response)

    def test_read_report_success(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{"report_id": 1}])
        response = self.disaster_model.ReadReport(report_id=1)
        self.assertIsNotNone(response)
        self.assertEqual(response[0]['report_id'], 1)

    def test_read_report_failure(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        response = self.disaster_model.ReadReport(report_id=99)
        self.assertIsNone(response)

    def test_update_report_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.disaster_model.UpdateReport(1, latitude=41.0)
        self.assertTrue(result)

    def test_update_report_failure(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.disaster_model.UpdateReport(1, latitude=41.0)
        self.assertFalse(result)

    def test_delete_report_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.disaster_model.DeleteReport(1)
        self.assertTrue(result)

    def test_delete_report_failure(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=400)
        result = self.disaster_model.DeleteReport(99)
        self.assertFalse(result)

    # Edge Test Cases
    
    # Test creating a report with missing mandatory fields
    def test_create_report_missing_fields(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.disaster_model.CreateReport(
            report_id=None,  # Missing report_id
            user_id=1,
            timestamp="2024-12-01T12:00:00Z",
            latitude=40.7128,
            longitude=-74.0060,
            address="NYC",
            weather_event_type="Flood",
            weather_event_severity="Severe",
            weather_event_description="Severe flooding in NYC"
        )
        self.assertIsNone(response)

    # Test creating a report with invalid coordinates
    def test_create_report_invalid_coordinates(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.disaster_model.CreateReport(
            report_id=1,
            user_id=1,
            timestamp="2024-12-01T12:00:00Z",
            latitude=999.9999,  # Invalid latitude
            longitude=-74.0060,
            address="NYC",
            weather_event_type="Flood",
            weather_event_severity="Severe",
            weather_event_description="Invalid latitude"
        )
        self.assertIsNone(response)

    # Test reading a report with nonexistent user
    def test_read_report_nonexistent_user(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        response = self.disaster_model.ReadReport(user_id=99999)  # Non-existent user_id
        self.assertIsNone(response)

    # Test updating a report with invalid ID
    def test_update_report_invalid_id(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=404)
        result = self.disaster_model.UpdateReport(99999, latitude=41.0)  # Nonexistent report_id
        self.assertFalse(result)

    # Test deleting a report with invalid ID
    def test_delete_report_invalid_id(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=404)
        result = self.disaster_model.DeleteReport(99999)  # Nonexistent report_id
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()
