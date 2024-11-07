from fastapi.testclient import TestClient
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import app

client = TestClient(app)


def get_auth_token():
    login_data = {
        "username": "Sudhan1221",
        "password": "ActuallySecure"
    }
    response = client.post("auth/login", json=login_data)
    assert response.status_code == 200, "Failed to get auth token"
    return response.json()["access_token"]


def test_protected_token_verification():
    # Get a valid auth token
    token = get_auth_token()

    # Send GET request to the /verifytoken endpoint with the Authorization header
    response = client.get(
        "protected/verifytoken",
        headers={"Authorization": f"Bearer {token}"}
    )

    # Check if the response status code is 200 (OK)
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Verify the response message and username
    response_json = response.json()
    assert response_json["message"] == "This is a protected route", "Unexpected response message"
    assert "username" in response_json, "Expected 'username' in response"

    print("ProtectedTokenVerification test passed. User verified:", response_json)
