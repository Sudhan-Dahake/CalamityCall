from fastapi import APIRouter, HTTPException, Depends, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationCreate, NotificationResponse, NotificationHistoryRequest, NotificationHistoryResponse
from ...supabase_db import AuthService


router = APIRouter()

AuthServiceObj = AuthService()

#Create Notification
@router.post("/", status_code=status.HTTP_201_CREATED)
async def CreateNotification(notification: NotificationCreate, username: str = Depends(AuthServiceObj.VerifyJWT)):
    NotifModel = NotificationModel()

    success = NotifModel.CreateNotification(**notification.model_dump(exclude_none=True))

    if success:
        return {"Message": "Notification created successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to create Notification")

@router.get("/immediate", response_model=NotificationResponse)
async def GetImmediateNotification(username: str = Depends(AuthServiceObj.VerifyJWT)):
    NotifModel = NotificationModel()

    notification = NotifModel.GetNotifToDisplayImmediately()

    if notification:
        if notification.get('preparationsteps') is None:
            notification.pop('preparationsteps')

        if notification.get('activesteps') is None:
            notification.pop('activesteps')

        if notification.get('recoverysteps') is None:
            notification.pop('recoverysteps')

        return notification

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No notifications found")

@router.get("/history", response_model=NotificationHistoryResponse)
async def GetNotificationHistory(timeframe: str = "1 month ago", username: str = Depends(AuthServiceObj.VerifyJWT)):
    NotifModel = NotificationModel()

    NotifDict = NotifModel.GetNotifToDisplayForHistory(timeFrame=timeframe)

    if NotifDict:
        if NotifDict.get('preparationsteps') is None:
            NotifDict.pop('preparationsteps')

        if NotifDict.get('activesteps') is None:
            NotifDict.pop('activesteps')

        if NotifDict.get('recoverysteps') is None:
            NotifDict.pop('recoverysteps')

        NotifList = list(value for key, value in NotifDict.items())

        return {"Notifications": NotifList}

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No notifications found for the specified time frame")

@router.get("/delete")
async def DeleteNotifications(username: str = Depends(AuthServiceObj.VerifyJWT)):
    NotifModel = NotificationModel()

    response = NotifModel.DeleteNotification()

    if not response:
        return {"message": "Notifications older than 6 months deleted successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to delete Notifications")