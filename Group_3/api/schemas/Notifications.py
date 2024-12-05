from pydantic import BaseModel, field_validator, ValidationError
import re
from datetime import datetime


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

    @field_validator("notifdate")
    def validate_notifdate(cls, value):
        # Regex to match YYYY-MM-DD followed by optional time (24-hour format)
        date_regex = r"^\d{4}-\d{2}-\d{2}( \d{2}:\d{2}:\d{2})?$"
        if not re.match(date_regex, value):
            raise ValueError(
                "notifdate must be in 'YYYY-MM-DD HH:MM:SS' or 'YYYY-MM-DD' format")

        # If time is included, validate date and time
        try:
            if " " in value:
                datetime.strptime(value, "%Y-%m-%d %H:%M:%S")
            else:
                datetime.strptime(value, "%Y-%m-%d")
        except ValueError:
            raise ValueError("notifdate contains invalid date or time values")

        return value


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
