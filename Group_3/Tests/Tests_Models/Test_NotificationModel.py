import unittest
from unittest.mock import patch, MagicMock
from datetime import datetime, date
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import NotificationModel


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
            notiforigin='Test',
            longitude=10.0,
            latitude=20.0,
            city='Waterloo',
            disastertype='Tornado',
            disasterlevel=3,
            notifdate=str(date(2024, 6, 12))
        )

        # Assert that the notification was created successfully
        self.assertTrue(result)
        self.assertEqual(len(self.notification_model.NotifID), 1)
        self.assertEqual(self.notification_model.NotifID[0]['NotifID'], 1)

    def test_create_notification_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        # Test CreateNotification function
        result = self.notification_model.CreateNotification(
            notiforigin='Test',
            longitude=10.0,
            latitude=20.0
        )

        # Assert that the notification creation failed
        self.assertFalse(result)
        self.assertEqual(len(self.notification_model.NotifID), 0)

    def test_get_notif_to_display_immediately_success(self):
        # Mock a successful response from Supabase client for selecting latest notification
        self.mock_client.from_().select().order().limit().execute.return_value = MagicMock(
            data=[{'notifid': 1, 'notifdate': '2024-06-12'}])

        # Test GetNotifToDisplayImmediately function
        result = self.notification_model.GetNotifToDisplayImmediately()

        # Assert that the correct notification is returned
        self.assertIsNotNone(result)
        self.assertEqual(result['notifid'], 1)
        self.assertEqual(result['notifdate'], '2024-06-12')

    def test_get_notif_to_display_immediately_failure(self):
        # Mock a failure response from Supabase client (no data returned)
        self.mock_client.from_().select().order().limit(
        ).execute.return_value = MagicMock(data=None)

        # Test GetNotifToDisplayImmediately function
        result = self.notification_model.GetNotifToDisplayImmediately()

        # Assert that None is returned when no notification is found
        self.assertIsNone(result)


if __name__ == '__main__':
    unittest.main()
