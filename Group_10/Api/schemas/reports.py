from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class DisasterReportCreate(BaseModel):
    user_id: int
    timestamp: datetime
    latitude: float
    longitude: float
    address: str
    weather_event_type: str
    weather_event_severity: str
    weather_event_description: str
