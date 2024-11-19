from pydantic import BaseModel, Field
from datetime import datetime

class FirebaseTokenCreate(BaseModel):
    fcmtoken: str
    userid: int | None = None
    deviceid: str
    notificationtype: str = "push"
    created_at: datetime = Field(default_factory=datetime.now)
    updated_at: datetime = Field(default_factory=datetime.now)


class FirebaseTokenUpdate(BaseModel):
    fcmtoken: str | None = None
    userid: int | None = None
    notificationtype: str | None = None