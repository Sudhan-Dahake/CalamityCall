from .firebase_cloud_messaging import FCMClient
from ...supabase_db.models.FirebaseTokenStorage import FirebaseTokenStorageModel

class NotificationManager:
    def __init__(self):
        self.FCMClient = FCMClient()
        self.TokenStorage = FirebaseTokenStorageModel()


    def SendNotificationsToAll(self, title, body):
        unregisteredTokens = self.TokenStorage.getAllUnregisteredTokens()

        if unregisteredTokens:
            for token in unregisteredTokens:
                response = self.FCMClient.SendNotification(token=token, title=title, body=body)
                print(response)


        registeredTokens = self.TokenStorage.getAllRegisteredTokens()

        if registeredTokens:
            for record in registeredTokens:
                token = record["fcmtoken"]
                notificationtype = record["notificationtype"]

                if (notificationtype == "popup"):
                    self.FCMClient.SendNotification(token=token, title=title, body=body, isPopup=True)

                else:
                    self.FCMClient.SendNotification(token=token, title=title, body=body)