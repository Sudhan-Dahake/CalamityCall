from pydantic import BaseModel


class NotificationCreate(BaseModel):
    notiforigin: str
    longitude: float
    latitude: float
    city: str
    disastertype: str
    disasterlevel: int
    notifdate: str


class NotificationResponse(BaseModel):
    notiforigin: str
    longitude: float
    latitude: float
    city: str
    disastertype: str
    disasterlevel: int
    notifdate: str


class NotificationHistoryRequest(BaseModel):
    timeFrame: str = "1 months ago"


class NotificationHistoryResponse(BaseModel):
    Notifications: list[NotificationResponse]
