from pydantic import BaseModel

class TokenRegistrationRequest(BaseModel):
    fcmtoken: str
    userid: int | None = None
    deviceid : str

class NotificationManagerSendNotification(BaseModel):
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

class FCMClientSendNotification(BaseModel):
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
    force_popup: bool = False