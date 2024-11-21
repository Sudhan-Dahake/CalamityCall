from pydantic import BaseModel

class NotificationOnValues(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None