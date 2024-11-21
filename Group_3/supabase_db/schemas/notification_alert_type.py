from pydantic import BaseModel
from typing import Literal


class NotificationAlertTypeValues(BaseModel):
    preferenceid: int | None = None
    watch: Literal["Push", "Popup", None] = None
    warning: Literal["Push", "Popup", None] = None
    urgent: Literal["Push", "Popup", None] = None
    critical: Literal["Push", "Popup", None] = None
