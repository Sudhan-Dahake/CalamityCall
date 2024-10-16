import unittest
from unittest.mock import patch, MagicMock
from datetime import datetime, date
from Group_3.supabase_db.models.Notification import NotificationModel


class TestNotificationModel(unittest.TestCase):
    # Mock the create_client from supabase
    @patch('Group_3.supabase_db.models.Notification.create_client')
    def setUp(self, mock_create_client):
        # Set up a mock Supabase client for use in all tests
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client

        # Initialize the NotificationModel with the mock client
        self.notification_model = NotificationModel()

    def test_create_notification_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(
            data=[{'NotifID': 1}])

        # Test CreateNotification function
        result = self.notification_model.CreateNotification(
            NotifOrigin='Test', Longitude=10.0, Latitude=20.0, City='Waterloo', DisasterType='Tornado', DisasterLevel=3, NotifDate=str(date(2024, 6, 12)))

        # Assert that the notification was created successfully
        self.assertTrue(result)
        self.assertEqual(len(self.notification_model.NotifID), 1)
        self.assertEqual(self.notification_model.NotifID[0]['NotifID'], 1)

    def test_create_notification_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        # Test CreateNotification function
        result = self.notification_model.CreateNotification(
            NotifOrigin='Test', Longitude=10.0, Latitude=20.0)

        # Assert that the notification creation failed
        self.assertFalse(result)
        self.assertEqual(len(self.notification_model.NotifID), 0)


if __name__ == '__main__':
    unittest.main()
