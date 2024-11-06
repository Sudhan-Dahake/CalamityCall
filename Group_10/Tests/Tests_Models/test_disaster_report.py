import unittest
from unittest.mock import patch, MagicMock

from Group_10 import DisasterReportsModel

class TestDisasterReportsModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.disaster_reports.create_client')  # Replace with actual module path
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.disaster_model = DisasterReportsModel()

    def test_create_report_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{'report_id': 'test-uuid'}])
        result = self.disaster_model.CreateReport(
            report_id="test-uuid",
            user_id=123,  
            timestamp="2023-10-10T10:00:00Z",
            latitude=37.7749,
            longitude=-122.4194,
            address="123 Main St",
            weather_event_type="flood",
            weather_event_severity="severe",
            weather_event_description="Flooding in the area"
        )
        self.assertIsNotNone(result)
        self.assertEqual(result['report_id'], 'test-uuid')

    def test_read_report_by_user_id(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{'report_id': 'test-uuid'}])
        result = self.disaster_model.ReadReport(user_id=123)
        self.assertIsNotNone(result)
        self.assertEqual(result[0]['report_id'], 'test-uuid')

    def test_update_report_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.disaster_model.UpdateReport(
            report_id="test-uuid",
            latitude=37.7749,
            longitude=-122.4194,
            address="456 Elm St"
        )
        self.assertTrue(result)

    def test_delete_report_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.disaster_model.DeleteReport(report_id="test-uuid")
        self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()