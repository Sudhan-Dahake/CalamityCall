import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import UserServices, UserModel


def test_authenticate_user():
    # Set up a mock user with UserModel and inject into UserServices
    mock_user_model = UserModel()
    user_services = UserServices(userModelObj=mock_user_model)

    # Assume `CreateUser` is tested separately and user exists in the database
    mock_user_model.CreateUser(username="test_user_sudhan", password="test_password", age=25,
                               address="123 Test St", zip_code="12345", city="Test City")

    # Test valid authentication
    assert user_services.AuthenticateUser(
        username="test_user_sudhan", password="test_password"), "User authentication failed with correct credentials"

    # Test invalid authentication
    assert not user_services.AuthenticateUser(
        username="test_user_sudhan", password="wrong_password"), "User authenticated with incorrect password"

    print("AuthenticateUser test passed.")


def test_get_preference_id_from_user():
    # Set up a mock user with UserModel and inject into UserServices
    mock_user_model = UserModel()
    user_services = UserServices(userModelObj=mock_user_model)

    # Test retrieving preference ID
    preference_id = user_services.GetPreferenceIDFromUser(
        username="test_user")
    assert preference_id is not None, f"Expected preference ID 10 but got {preference_id}"

    print("GetPreferenceIDFromUser test passed.")
