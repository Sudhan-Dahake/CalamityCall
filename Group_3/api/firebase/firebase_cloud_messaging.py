import os
import json
import requests
from google.oauth2 import service_account
from dotenv import load_dotenv
from google.auth.transport.requests import Request
import google.oauth2.id_token

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
        # if not self.credentials.valid:
        #     self.credentials.refresh(Request())

        # print(self.credentials.valid)

        auth_req = Request()
        id_token = google.oauth2.id_token.fetch_id_token(auth_req, self.url)

        headers = {
            'Authorization': f"Bearer {id_token}",
            'Content-type': 'application/json',
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
