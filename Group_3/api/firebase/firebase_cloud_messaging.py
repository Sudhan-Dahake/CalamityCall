import os
import json
import requests
from google.oauth2 import service_account
from dotenv import load_dotenv
from google.auth.transport.requests import Request
from ..schemas.FirebaseToken import FCMClientSendNotification

load_dotenv()


class FCMClient:
    def __init__(self):
        self.credentials = self.__LoadCredentials()
        self.firebase_projectID = os.getenv("FIREBASE_PROJECT_ID")
        self.url = f"https://fcm.googleapis.com/v1/projects/{self.firebase_projectID}/messages:send"

    def __LoadCredentials(self):
        secret_file_path = "/etc/secrets/firebase-credentials.json"
        return service_account.Credentials.from_service_account_file(secret_file_path, scopes=["https://www.googleapis.com/auth/firebase.messaging"])

    def SendNotification(self, token, NotificationModel: FCMClientSendNotification, isPopup: bool = False):
        self.credentials.refresh(Request())

        if isPopup:
            NotificationModel.force_popup = True

        headers = {
            'Authorization': f"Bearer {self.credentials.token}",
            'Content-type': 'application/json; UTF-8',
        }

         # Convert all values in the data payload to strings
        data_payload = {key: str(value) for key, value in NotificationModel.model_dump(exclude_none=True).items()}

        message = {
            "message": {
                "token": token,

                "data": data_payload,

                "android": {
                    "priority": "high"
                }
            }
        }

        response = requests.post(self.url, headers=headers, data=json.dumps(message))

        print(response.json())

        return response.json()
