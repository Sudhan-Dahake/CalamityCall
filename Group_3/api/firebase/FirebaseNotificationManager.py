from .firebase_cloud_messaging import FCMClient
from ...supabase_db.models.FirebaseTokenStorage import FirebaseTokenStorageModel
from ..schemas.FirebaseToken import NotificationManagerSendNotification, FCMClientSendNotification

class NotificationManager:
    def __init__(self):
        self.FCMClient = FCMClient()
        self.TokenStorage = FirebaseTokenStorageModel()


    def SendNotificationsToAll(self, NotificationModel: NotificationManagerSendNotification):
        unregisteredTokens = self.TokenStorage.getAllUnregisteredTokens()

        if unregisteredTokens:
            for token in unregisteredTokens:
                response = self.FCMClient.SendNotification(
                    token=token, NotificationModel=FCMClientSendNotification(**NotificationModel.model_dump(exclude_none=True)))


        registeredTokens = self.TokenStorage.getAllRegisteredTokens()

        if registeredTokens:
            for record in registeredTokens:
                token = record["fcmtoken"]
                notificationtype = record["notificationtype"]

                if (notificationtype == "popup"):
                    self.FCMClient.SendNotification(token=token, NotificationModel=FCMClientSendNotification(**NotificationModel.model_dump(exclude_none=True)), isPopup=True)

                else:
                    self.FCMClient.SendNotification(token=token, NotificationModel=FCMClientSendNotification(**NotificationModel.model_dump(exclude_none=True)))