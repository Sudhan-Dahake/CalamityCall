from pydantic import BaseModel


class NotificationCreate(BaseModel):
    notiforigin: str
    longitude: float
    latitude: float
    city: str
    disastertype: str
    disasterlevel: int
    notifdate: str
    notiftime: str | None = None
    preparationsteps: str | None = None
    activesteps: str | None = None
    recoverysteps: str | None = None


class NotificationResponse(BaseModel):
    notiforigin: str
    longitude: float
    latitude: float
    city: str
    disastertype: str
    disasterlevel: int
    notifdate: str
    preparationsteps: str | None = None
    activesteps: str | None = None
    recoverysteps: str | None = None


class NotificationHistoryRequest(BaseModel):
    timeFrame: str = "1 month ago"


class NotificationHistoryResponse(BaseModel):
    Notifications: list[NotificationResponse]


class SingleNotificationActiveResponse(BaseModel):
    notiforigin: str
    longitude: float
    latitude: float
    city: str
    disastertype: str
    disasterlevel: int
    notifdate: str
    notiftime: str | None = None
    preparationsteps: str | None = None
    activesteps: str | None = None
    recoverysteps: str | None = None


class NotificationActiveResponse(BaseModel):
    Notifications: list[SingleNotificationActiveResponse]
