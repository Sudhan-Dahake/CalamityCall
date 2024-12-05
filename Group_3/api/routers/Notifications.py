from fastapi import APIRouter, HTTPException, Depends, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationResponse, NotificationHistoryRequest, NotificationHistoryResponse, NotificationActiveResponse
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


@router.get("/active", response_model=NotificationActiveResponse)
async def get_active_notifications():
    NotifModel = NotificationModel()

    try:
        # Call the GetRecentNotifications function
        recent_notifications = NotifModel.GetActiveNotifications(
            "Canada/Eastern")

        # If no notifications are found, raise a 404 exception
        if not recent_notifications["Notifications"]:
            raise HTTPException(
                status_code=404, detail="No notifications found")

        # Return the response
        return recent_notifications

    except Exception as e:
        # Handle any unexpected errors
        raise HTTPException(
            status_code=500, detail=f"Internal Server Error: {str(e)}")


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