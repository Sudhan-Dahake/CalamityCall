from pydantic import BaseModel

class FlashingValues(BaseModel):
    preferenceid: int | None = None
    watch: bool | None = None
    warning: bool | None = None
    urgent: bool | None = None
    critical: bool | None = None