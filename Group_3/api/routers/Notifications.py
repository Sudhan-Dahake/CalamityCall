from fastapi import APIRouter, HTTPException, Depends, status
from .. import NotificationModel
from ..schemas.Notifications import NotificationCreate, NotificationResponse, NotificationHistoryRequest, NotificationHistoryResponse

router = APIRouter()

#Create Notification
@router.post("/", status_code=status.HTTP_201_CREATED)
async def CreateNotification(notification: NotificationCreate):
    NotifModel = NotificationModel()

    success = NotifModel.CreateNotification(**notification.dict())

    if success:
        return {"Message": "Notification created successfully"}
    
    raise HTTPException(status_code=500, detail="Failed to create Notification")

@router.get("/immediate", response_model=NotificationResponse)
async def GetImmediateNotification():
    NotifModel = NotificationModel()

    notification = NotifModel.GetNotifToDisplayImmediately()

    if notification:
        return notification
    
    raise HTTPException(status_code=404, detail="No notifications found")

@router.get("/history", response_model=NotificationHistoryResponse)
async def GetNotificationHistory(timeframe: str = "1 month ago"):
    NotifModel = NotificationModel()

    NotifDict = NotifModel.GetNotifToDisplayForHistory(timeFrame=timeframe)

    if NotifDict:
        NotifList = list(value for key, value in NotifDict.items())

        return {"Notifications": NotifList}
    
    raise HTTPException(status_code=404, detail="No notifications found for the specified time frame")

@router.get("/delete")
async def DeleteNotifications():
    NotifModel = NotificationModel()

    response = NotifModel.DeleteNotification()

    if not response:
        return {"message": "Notifications older than 6 months deleted successfully"}
    
    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to delete Notifications")