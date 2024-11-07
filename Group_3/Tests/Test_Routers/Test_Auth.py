from fastapi.testclient import TestClient
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import app


client = TestClient(app)


def test_login():
    # Create a sample request body for login
    login_data = {
        "username": "Sudhan1221",
        "password": "ActuallySecure"
    }

    # Send POST request to the /login endpoint
    response = client.post("auth/login", json=login_data)

    # Assert the response status code
    assert response.status_code == 200, "Expected 200 OK response"

    # Parse the JSON response
    response_json = response.json()

    # Verify that access_token and refresh_token are in the response
    assert "access_token" in response_json, "Access token not in response"
    assert "refresh_token" in response_json, "Refresh token not in response"
    assert response_json["token_type"] == "bearer", "Token type is incorrect"

    print("Login test passed. Tokens received:", response_json)


def test_signup():
    # Test data for signup
    signup_data = {
        "username": "test_user",
        "password": "TestPassword123",
        "age": 25,
        "address": "123 Test Street",
        "zip_code": "12345",
        "city": "Test City"
    }

    # Send POST request to /signup endpoint
    response = client.post("auth/signup", json=signup_data)

    # Check response status code
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Check response data
    response_json = response.json()
    assert response_json["message"] == "User Created Successfully", "User creation message not as expected"

    print("Signup test passed. User created successfully:", response_json)
