import os
import json
import requests
from google.oauth2 import service_account
from dotenv import load_dotenv
from google.auth.transport.requests import Request

load_dotenv()

class FCMClient:
    def __init__(self):
        self.credentials = self.__LoadCredentials()
        self.firebase_projectID = os.getenv("FIREBASE_PROJECT_ID")
        self.url = f"https://fcm.googleapis.com/v1/projects/{self.firebase_projectID}/messages:send"

    def __LoadCredentials(self):
        secret_file_path = "/etc/secrets/firebase-credentials.json"
        return service_account.Credentials.from_service_account_file(secret_file_path)

    def SendNotification(self, token, title, body, isPopup = False):
        print(self.credentials.valid)

        if not self.credentials.valid:
            self.credentials.refresh(Request())

        print(self.credentials.valid)

        headers = {
            'Authorization': f"Bearer {self.credentials.token}",
            'Content-type': 'application/json; UTF-8',
        }

        message = {
            "message": {
                "token": token,
                "notification": {
                    "title": title,
                    "body": body,
                },

                "data": {
                    "force_popup": "true" if isPopup else "false"
                }
            }
        }


        response = requests.post(self.url, headers=headers, data=json.dumps(message))

        return response.json()
