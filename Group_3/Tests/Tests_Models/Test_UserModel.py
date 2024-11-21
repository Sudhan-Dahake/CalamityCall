import unittest
from unittest.mock import patch, MagicMock
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import UserModel


class TestUserModel(unittest.TestCase):
    # Mock the create_client from supabase
    @patch('Group_3.supabase_db.models.User.create_client')  # Mock create_client for Supabase
    def setUp(self, mock_create_client):
        # Set up a mock Supabase client for use in all tests
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client

        # Initialize the UserModel with the mock client
        self.user_model = UserModel()

    def test_create_user_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(
            data=[{'userid': 1}]
        )

        # Call CreateUser method
        result = self.user_model.CreateUser(
            username="test_user",
            password="test_pass",
            preference_id=1,
            age=25,
            address="123 Test Street",
            zip_code="12345",
            city="TestCity"
        )

        # Assert that the user was created successfully
        self.assertEqual(result, 1)
        self.mock_client.from_().insert().execute.assert_called_once()

    def test_create_user_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        # Call CreateUser method
        result = self.user_model.CreateUser(
            username="test_user"
        )

        # Assert that the user creation failed
        self.assertFalse(result)

    def test_get_user_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(
            data=[{
                'userid': 1,
                'username': 'test_user',
                'age': 25,
                'city': 'TestCity'
            }]
        )

        # Call GetUser method
        result = self.user_model.GetUser(userID=1)

        # Assert that the user was retrieved successfully
        self.assertEqual(result['userid'], 1)
        self.mock_client.from_().select().eq().execute.assert_called_once()

    def test_get_user_failure(self):
        # Mock failure response from Supabase client (no data found)
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)

        # Call GetUser method
        result = self.user_model.GetUser(userID=1)

        # Assert that the user retrieval failed
        self.assertIsNone(result)
        self.mock_client.from_().select().eq().execute.assert_called_once()

    def test_update_user_success(self):
        # Mock successful response from Supabase client
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(data=True)

        # Call UpdateUser method
        result = self.user_model.UpdateUser(
            user_id=1,
            currentUsername="updated_user",
            age=30
        )

        # Assert that the user was updated successfully
        self.assertTrue(result)
        self.mock_client.from_().update().eq().execute.assert_called_once()

    def test_update_user_failure(self):
        # Mock failure response from Supabase client
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(data=None)

        # Call UpdateUser method
        result = self.user_model.UpdateUser(
            user_id=1,
            currentUsername="updated_user"
        )

        # Assert that the user update failed
        self.assertFalse(result)


if __name__ == '__main__':
    unittest.main()
