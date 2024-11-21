from fastapi import APIRouter, HTTPException, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationCreate
from ..firebase.FirebaseNotificationManager import NotificationManager

router = APIRouter()

notificationManager = NotificationManager()

#Endpoint to receive Notifications from NWS
@router.post("/NWS", status_code=status.HTTP_201_CREATED)
async def CreateNotification(notification: NotificationCreate):
    NotifModel = NotificationModel()

    success = NotifModel.CreateNotification(**notification.model_dump(exclude_none=True))

    if success:
        title = f"{notification.disastertype} Alert in {notification.city}"
        body = f"Level {notification.disasterlevel}"

        notificationManager.SendNotificationsToAll(NotificationModel=notification)

        return {"Message": "Notification created and broadcasted successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to create Notification")


#Endpoint to receive Notifications for Emergency Services
@router.post("/EMS", status_code=status.HTTP_201_CREATED)
async def CreateNotification(notification: NotificationCreate):
    NotifModel = NotificationModel()

    success = NotifModel.CreateNotification(**notification.model_dump(exclude_none=True))

    if success:
        title = f"{notification.disastertype} Alert in {notification.city}"
        body = f"Level {notification.disasterlevel} Disaster: {notification.preparationsteps}"

        notificationManager.SendNotificationsToAll(NotificationModel=notification)

        return {"Message": "Notification created and broadcasted successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to create Notification")