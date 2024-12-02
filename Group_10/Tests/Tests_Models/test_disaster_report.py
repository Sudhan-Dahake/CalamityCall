import unittest
from unittest.mock import patch, MagicMock

from Group_10.models.disaster_reports import DisasterReportsModel

class TestDisasterReportsModel(unittest.TestCase):

    @patch('Group_10.models.disaster_reports.create_client')
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

if __name__ == '__main__':
    unittest.main()
