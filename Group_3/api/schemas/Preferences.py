from pydantic import BaseModel

# class PreferenceCreate(BaseModel):
#     notificationtype: str = "Push"
#     disastertype: str = "All"
#     severitytype: int = 3
#     notifflashing: bool = True
#     texttospeech: bool = True
#     notiftimeframe: str = "6 months ago"

class PreferenceCreateResponse(BaseModel):
    PreferenceID: int

class PreferenceResponse(BaseModel):
    notificationtype: str
    disastertype: str
    severitytype: int
    notifflashing: bool
    texttospeech: bool
    notiftimeframe: str

class PreferenceUpdate(BaseModel):
    notificationtype: str | None = None
    disastertype: str | None = None
    severitytype: int | None = None
    notifflashing: bool | None = None
    texttospeech: bool | None = None
    notiftimeframe: str | None = None