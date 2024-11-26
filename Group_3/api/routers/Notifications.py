from fastapi import APIRouter, HTTPException, Depends, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationResponse, NotificationHistoryRequest, NotificationHistoryResponse
from ...supabase_db import AuthService


router = APIRouter()

AuthServiceObj = AuthService()

@router.get("/immediate", response_model=NotificationResponse, response_model_exclude_none=True)
async def GetImmediateNotification(username: str = Depends(AuthServiceObj.VerifyJWT)):
    NotifModel = NotificationModel()

    notification = NotifModel.GetNotifToDisplayImmediately()

    if notification:
        return notification

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No notifications found")

@router.post("/history", response_model=NotificationHistoryResponse, response_model_exclude_none=True)
async def GetNotificationHistory(historyRequest: NotificationHistoryRequest):
    NotifModel = NotificationModel()

    NotifDict = NotifModel.GetNotifToDisplayForHistory(timeFrame=historyRequest.timeFrame)

    if NotifDict:
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