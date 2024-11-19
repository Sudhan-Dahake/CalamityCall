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

        # Inspect the credentials object
        print("Project ID:", self.credentials.project_id)
        print("Client Email:", self.credentials.service_account_email)
        print("Token URI:", self.credentials._token_uri)
        print("Scopes:", self.credentials.scopes)
        print("token: ", self.credentials.token)

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
