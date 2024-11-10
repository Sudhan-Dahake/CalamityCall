from pydantic import BaseModel
from . import NotificationOnValues, NoiseValues, FlashingValues, NotificationAlertTypeValues, TextToSpeechValues

class MainPreferenceValues(BaseModel):
    pass

class UpdatedPreferenceValues(BaseModel):
    tableName: str
    preferenceid: int
    data: NotificationOnValues | NoiseValues | FlashingValues | NotificationAlertTypeValues | TextToSpeechValues