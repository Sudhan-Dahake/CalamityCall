from pydantic import BaseModel
from typing import Literal

# class PreferenceCreate(BaseModel):
#     notificationtype: str = "Push"
#     disastertype: str = "All"
#     severitytype: int = 3
#     notifflashing: bool = True
#     texttospeech: bool = True
#     notiftimeframe: str = "6 months ago"

class NotificationOnRequest(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None


class NoiseRequest(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None


class FlashingRequest(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None


class NotificationAlertTypeRequest(BaseModel):
    preferenceid: int | None = None
    watch: Literal["Push", "Popup", None] = None
    warning: Literal["Push", "Popup", None] = None
    urgent: Literal["Push", "Popup", None] = None
    critical: Literal["Push", "Popup", None] = None


class TextToSpeechRequest(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None


class PreferenceResponse(BaseModel):
    notification_on: NotificationOnRequest | None = None
    noise: NoiseRequest | None = None
    flashing: FlashingRequest | None = None
    notification_alert_type: NotificationAlertTypeRequest | None = None
    text_to_speech: TextToSpeechRequest | None = None


class PreferenceUpdate(BaseModel):
    tableName: str
    preferenceid: int | None = None
    data: NotificationOnRequest | NoiseRequest | FlashingRequest | NotificationAlertTypeRequest | TextToSpeechRequest