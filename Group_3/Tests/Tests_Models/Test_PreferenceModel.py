import unittest
from unittest.mock import patch, MagicMock
from datetime import date

import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import PreferencesModel


class TestPreferencesModel(unittest.TestCase):
    # Mock the create_client from supabase
    @patch('Group_3.supabase_db.models.Preference.create_client')
    def setUp(self, mock_create_client):
        # Set up a mock Supabase client for use in all tests
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client

        # Initialize the PreferencesModel with the mock client
        self.preferences_model = PreferencesModel()

    def test_create_preference_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(
            data=[{'preferenceid': 1}]
        )

        # Call CreatePreference method
        result = self.preferences_model.CreatePreference(
            NotificationType="Email",
            DisasterType="Flood",
            SeverityType=4,
            NotifFlashing=False,
            TextToSpeech=False,
            NotifTimeFrame="3 months ago"
        )

        # Assert that the preference was created successfully
        self.assertEqual(result, 1)
        self.mock_client.from_().insert().execute.assert_called_once()

    def test_create_preference_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        # Call CreatePreference method
        result = self.preferences_model.CreatePreference(
            NotificationType="Push"
        )

        # Assert that the preference creation failed
        self.assertIsNone(result)

    def test_get_preference_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(
            data=[{
                'preferenceid': 1,
                'notificationtype': 'Push',
                'disastertype': 'All',
                'severitytype': 3
            }]
        )

        # Call GetPreference method
        result = self.preferences_model.GetPreference(preference_id=1)

        # Assert that the preference was retrieved successfully
        self.assertEqual(result['preferenceid'], 1)
        self.mock_client.from_().select().eq().execute.assert_called_once()

    def test_get_preference_failure(self):
        # Mock failure response from Supabase client (no data found)
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)

        # Call GetPreference method
        result = self.preferences_model.GetPreference(preference_id=1)

        # Assert that the preference retrieval failed
        self.assertIsNone(result)
        self.mock_client.from_().select().eq().execute.assert_called_once()

    def test_update_preference_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(data=True)

        # Call UpdatePreference method
        result = self.preferences_model.UpdatePreference(
            preference_id=1,
            NotificationType="Push"
        )

        # Assert that the preference was updated successfully
        self.assertTrue(result)
        self.mock_client.from_().update().eq().execute.assert_called_once()

    def test_update_preference_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(data=None)

        # Call UpdatePreference method
        result = self.preferences_model.UpdatePreference(
            preference_id=1,
            NotificationType="Push"
        )

        # Assert that the preference update failed
        self.assertFalse(result)


if __name__ == '__main__':
    unittest.main()
