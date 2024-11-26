from fastapi import APIRouter, HTTPException
from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime
from ...supabase_db.models.disaster_reports import DisasterReportsModel

# Initialize router
router = APIRouter()

# Pydantic schema for incoming disaster reports
class Media(BaseModel):
    type: str
    url: str
    description: Optional[str] = None

class Location(BaseModel):
    latitude: float
    longitude: float
    address: str

class Event(BaseModel):
    type: str
    severity: str
    description: str

class DisasterReport(BaseModel):
    report_id: str
    user_id: int
    created_at: datetime
    location: Location
    event: Event
    media: Optional[List[Media]] = None

# Endpoint to create a disaster report
@router.post("/disaster-reports/")
async def create_disaster_report(report: DisasterReport):
    try:
        disaster_model = DisasterReportsModel()
        result = disaster_model.CreateReport(
            report_id=report.report_id,
            user_id=report.user_id,
            timestamp=report.created_at,
            latitude=report.location.latitude,
            longitude=report.location.longitude,
            address=report.location.address,
            weather_event_type=report.event.type,
            weather_event_severity=report.event.severity,
            weather_event_description=report.event.description,
        )
        return {"message": "Disaster report created successfully.", "report_id": result['report_id']}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
