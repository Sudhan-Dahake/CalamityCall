import unittest
from unittest.mock import MagicMock
from models.user_model import UserModel


class TestUserModel(unittest.TestCase):

    def setUp(self):
        # Creating a mock database client
        self.mock_client = MagicMock()
        self.user_model = UserModel(self.mock_client)
        
    # Test case to handle invalid user data when creating a new user.
    # In this case, the user data is missing (name=None, email=None, password=None).
    # We expect the result to be None since the data is invalid.
    def test_create_user_invalid_data(self):
        result = self.user_model.CreateUser(name=None, email=None, password=None)
        self.assertIsNone(result)

    # Test case for successfully creating a user with valid data.
    # We mock the client to simulate a successful database insertion and return a user ID.
    # The test checks that the user ID returned matches the expected value.
    def test_create_user_valid(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(status_code=200, data={"user_id": "test-user-id"})
        result = self.user_model.CreateUser(name="Proj Test", email="proj@gmail.com", password="password")
        self.assertIsNotNone(result)
        self.assertEqual(result['user_id'], "test-user-id")

    # Test case for reading a user when an invalid user ID is provided.
    # We mock the client to return an empty list to simulate that the user does not exist in the database.
    # The expected result is an empty list, indicating that no user was found for the given ID.
    def test_read_user_invalid_user_id(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[])
        result = self.user_model.ReadUser(user_id=999)
        self.assertEqual(result, [])

    # Test case for attempting to update a user that does not exist.
    # We mock the client to simulate a "not found" scenario by returning a 404 status code.
    # The expected result is False, indicating the update failed.
    def test_update_user_invalid_user_id(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=404)
        result = self.user_model.UpdateUser(user_id=999, name="Updated Name")
        self.assertFalse(result)

    # Test case for attempting to delete a user that does not exist.
    # We mock the client to simulate a "not found" scenario by returning a 404 status code.
    # The expected result is False, indicating the delete operation failed.
    def test_delete_user_invalid_user_id(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=404)
        result = self.user_model.DeleteUser(user_id=999)
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()