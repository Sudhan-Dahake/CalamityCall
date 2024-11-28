from fastapi import APIRouter, HTTPException, status
from pydantic import BaseModel
from typing import List, Optional
from datetime import datetime
import requests  # To send POST requests to Group 7
from ...supabase_db.models.disaster_reports import DisasterReportsModel

# Initialize router
router = APIRouter()

# Pydantic schemas for disaster reports
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
    # media: Optional[List[Media]] = []


# Group 7's API endpoint
GROUP_7_API_URL = "https://group7.example.com/api/process-disaster-reports/"  # Replace with actual URL


# Fetch a disaster report by report_id
@router.get("/disaster-reports/{report_id}")
async def get_disaster_report(report_id: str):
    try:
        disaster_model = DisasterReportsModel()
        report = disaster_model.ReadReport(report_id=report_id)
        if report:
            return report
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Disaster report not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Submit a new disaster report
@router.post("/disaster-reports/")
async def create_disaster_report(report: DisasterReport):
    try:
        disaster_model = DisasterReportsModel()
        # If no media is provided, use an empty list, not None
        # media_data = report.media if report.media else []

        result = disaster_model.CreateReport(
            report_id=report.report_id,
            user_id=report.user_id,
            timestamp=report.created_at.isoformat(),
            latitude=report.location.latitude,
            longitude=report.location.longitude,
            address=report.location.address,
            weather_event_type=report.event.type,
            weather_event_severity=report.event.severity,
            weather_event_description=report.event.description,
            # media=media_data,  # Pass the media directly as a list (empty or with data)
        )
       # Construct the JSON payload
        group_7_payload = {
            "disaster_reports": [
                {
                    "report_id": report.report_id,
                    "user_id": report.user_id,
                    "timestamp": report.created_at.isoformat(),
                    "latitude": report.location.latitude,
                    "longitude": report.location.longitude,
                    "address": report.location.address,
                    "weather_event_type": report.event.type,
                    "weather_event_severity": report.event.severity,
                    "weather_event_description": report.event.description,
                    "created_at": datetime.utcnow().isoformat()  # Use the current timestamp for the "created_at" field
                }
            ]
        }

        # Send POST request to Group 7
        group_7_response = requests.post(GROUP_7_API_URL, json=group_7_payload)

        if group_7_response.status_code != 200:
            raise HTTPException(
                status_code=500,
                detail=f"Failed to forward the report to Group 7. Response: {group_7_response.text}",
            )

        # Return success response
        return {
            "message": "Disaster report saved and forwarded successfully.",
            "report_id": report.report_id,
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")


# Retrieve all disaster reports
@router.get("/disaster-reports/")
async def get_all_disaster_reports():
    try:
        disaster_model = DisasterReportsModel()
        reports = disaster_model.ReadReport()
        if reports:
            return {"disaster_reports": reports}
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No disaster reports found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Update a disaster report
@router.put("/disaster-reports/{report_id}")
async def update_disaster_report(report_id: str, report: DisasterReport):
    try:
        disaster_model = DisasterReportsModel()
        success = disaster_model.UpdateReport(
            report_id=report_id,
            latitude=report.location.latitude,
            longitude=report.location.longitude,
            address=report.location.address,
            weather_event_type=report.event.type,
            weather_event_severity=report.event.severity,
            weather_event_description=report.event.description,
        )
        if success:
            return {"message": f"Report {report_id} updated successfully"}
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Disaster report not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


# Delete a disaster report
@router.delete("/disaster-reports/{report_id}")
async def delete_disaster_report(report_id: str):
    try:
        disaster_model = DisasterReportsModel()
        success = disaster_model.DeleteReport(report_id=report_id)
        if success:
            return {"message": f"Report {report_id} deleted successfully"}
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Disaster report not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
