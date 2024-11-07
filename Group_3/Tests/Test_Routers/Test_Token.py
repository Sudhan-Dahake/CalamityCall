from fastapi.testclient import TestClient
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import app

client = TestClient(app)


def get_tokens():
    login_data = {
        "username": "Sudhan1221",
        "password": "ActuallySecure"
    }
    response = client.post("auth/login", json=login_data)
    assert response.status_code == 200, "Failed to get tokens"
    tokens = response.json()
    return tokens["refresh_token"]


def test_refresh_jwt():
    # Get initial access and refresh tokens
    refresh_token = get_tokens()

    # Send POST request to the /JWT endpoint with the refresh token
    response = client.post(
        "generate/JWT",
        json={"refresh_token": refresh_token}
    )

    # Check if the response status code is 200 (OK)
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Verify the response contains a new access token
    response_json = response.json()
    assert "access_token" in response_json, "New access token not in response"
    assert response_json["token_type"] == "bearer", "Token type is incorrect"

    print("RefreshJWT test passed. New access token generated:", response_json)
