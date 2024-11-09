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


def test_create_notification():
    # Get a valid auth token
    token = get_auth_token()

    # Define the test data for notification creation
    notification_data = {
        "notiforigin": "TestSource",
        "longitude": 123.45,
        "latitude": 67.89,
        "city": "Test City",
        "disastertype": "Earthquake",
        "disasterlevel": 4,
        "notifdate": "2024-11-07"
    }

    # Send POST request to create notification
    response = client.post(
        "notifications/",
        json=notification_data,
        headers={"Authorization": f"Bearer {token}"}
    )

    # Check if the response is successful
    assert response.status_code == 201, f"Expected 201 but got {response.status_code}"
    response_json = response.json()
    assert response_json["Message"] == "Notification created successfully", "Unexpected response message"

    print("CreateNotification test passed. Notification created successfully.")


def test_get_immediate_notification():
    # Get a valid auth token
    token = get_auth_token()

    # Send GET request to the /immediate endpoint with the Authorization header
    response = client.get(
        "notifications/immediate",
        headers={"Authorization": f"Bearer {token}"}
    )

    # Check the response status code
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Parse and verify the response JSON structure
    response_json = response.json()
    assert "notiforigin" in response_json, "notiforigin not in response"
    assert "longitude" in response_json, "longitude not in response"
    assert "latitude" in response_json, "latitude not in response"
    assert "city" in response_json, "city not in response"
    assert "disastertype" in response_json, "disastertype not in response"
    assert "disasterlevel" in response_json, "disasterlevel not in response"
    assert "notifdate" in response_json, "notifdate not in response"

    print("GetImmediateNotification test passed. Latest notification:", response_json)


def test_get_notification_history():
    # Get a valid auth token
    token = get_auth_token()

    # Define the timeframe for the notification history
    params = {"timeframe": "1 month ago"}

    # Send GET request to the /history endpoint with the Authorization header
    response = client.get(
        "notifications/history",
        headers={"Authorization": f"Bearer {token}"},
        params=params
    )

    # Check the response status code
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Parse and verify the response JSON structure
    response_json = response.json()
    assert "Notifications" in response_json, "Expected 'Notifications' key in response"
    assert isinstance(response_json["Notifications"],
                      list), "Expected Notifications to be a list"

    # Check that each notification in the list has the required fields
    for notification in response_json["Notifications"]:
        assert "notiforigin" in notification, "notiforigin not in notification"
        assert "longitude" in notification, "longitude not in notification"
        assert "latitude" in notification, "latitude not in notification"
        assert "city" in notification, "city not in notification"
        assert "disastertype" in notification, "disastertype not in notification"
        assert "disasterlevel" in notification, "disasterlevel not in notification"
        assert "notifdate" in notification, "notifdate not in notification"

    print("GetNotificationHistory test passed. Notifications retrieved:", response_json)


def test_delete_notifications():
    # Get a valid auth token
    token = get_auth_token()

    # Send GET request to the /delete endpoint with the Authorization header
    response = client.get(
        "notifications/delete",
        headers={"Authorization": f"Bearer {token}"}
    )

    # Check if the response status code is 200 (OK)
    assert response.status_code == 200, f"Expected 200 OK but got {response.status_code}"

    # Verify the response message
    response_json = response.json()
    assert response_json["message"] == "Notifications older than 6 months deleted successfully", "Unexpected response message"

    print("DeleteNotifications test passed. Notifications deleted successfully.")
