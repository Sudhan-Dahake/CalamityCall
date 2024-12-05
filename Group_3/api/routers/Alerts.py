from fastapi import APIRouter, HTTPException, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationCreate
from ..firebase.FirebaseNotificationManager import NotificationManager
import pytz
from datetime import datetime

router = APIRouter()

notificationManager = NotificationManager()


#Endpoint to receive Notifications from NWS
@router.post("/NWS", status_code=status.HTTP_201_CREATED)
async def CreateNotification(notification: NotificationCreate):
    NotifModel = NotificationModel()

    if ' ' in notification.notifdate:
        date_part, time_part = notification.notifdate.split(' ', 1)

        notification.notifdate = date_part
        notification.notiftime = time_part

    else:
        canadian_time_zone = pytz.timezone('Canada/Eastern')

        notification.notiftime = datetime.now(canadian_time_zone).strftime("%I:%M:%S %p")


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

    if ' ' in notification.notifdate:
        date_part, time_part = notification.notifdate.split(' ', 1)

        notification.notifdate = date_part
        notification.notiftime = time_part

    else:
        canadian_time_zone = pytz.timezone('Canada/Eastern')

        notification.notiftime = datetime.now(canadian_time_zone).strftime("%I:%M:%S %p")

    success = NotifModel.CreateNotification(**notification.model_dump(exclude_none=True))

    if success:
        title = f"{notification.disastertype} Alert in {notification.city}"
        body = f"Level {notification.disasterlevel} Disaster: {notification.preparationsteps}"

        notificationManager.SendNotificationsToAll(NotificationModel=notification)

        return {"Message": "Notification created and broadcasted successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to create Notification")