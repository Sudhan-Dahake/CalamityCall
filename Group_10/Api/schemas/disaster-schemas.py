from pydantic import BaseModel, Field, validator
from typing import List, Optional
from datetime import datetime

class Media(BaseModel):
    type: str = Field(
        ..., 
        description="Type of media (image, video, or audio)",
        example="image"
    )
    url: str = Field(
        ..., 
        description="URL of the media file",
        example="https://example.com/flood.jpg"
    )
    description: Optional[str] = Field(
        None,
        description="Optional description of the media",
        example="Flooding in downtown area"
    )

    @validator('type')
    def validate_media_type(cls, v):
        allowed_types = ['image', 'video', 'audio']
        if v.lower() not in allowed_types:
            raise ValueError(f'Media type must be one of {allowed_types}')
        return v.lower()

class Location(BaseModel):
    latitude: float = Field(
        ...,
        ge=-90,
        le=90,
        description="Latitude coordinate between -90 and 90",
        example=37.7749
    )
    longitude: float = Field(
        ...,
        ge=-180,
        le=180,
        description="Longitude coordinate between -180 and 180",
        example=-122.4194
    )
    address: str = Field(
        ...,
        min_length=5,
        description="Physical address of the location",
        example="123 Main Street, San Francisco, CA 94105"
    )

    @validator('latitude', 'longitude')
    def round_coordinates(cls, v):
        return round(v, 6)

class Event(BaseModel):
    type: str = Field(
        ...,
        min_length=2,
        description="Type of weather event or disaster",
        example="flood"
    )
    severity: str = Field(
        ...,
        description="Severity level of the event (moderate, severe, extreme)",
        example="severe"
    )
    description: str = Field(
        ...,
        min_length=10,
        description="Detailed description of the event",
        example="Flash flooding affecting downtown area with water levels rising rapidly"
    )

    @validator('severity')
    def validate_severity(cls, v):
        allowed_severity = ['moderate', 'severe', 'extreme']
        if v.lower() not in allowed_severity:
            raise ValueError(f'Severity must be one of {allowed_severity}')
        return v.lower()

    @validator('type')
    def validate_event_type(cls, v):
        allowed_types = ['flood', 'fire', 'earthquake', 'hurricane', 'tornado', 'landslide', 'other']
        if v.lower() not in allowed_types:
            raise ValueError(f'Event type must be one of {allowed_types}')
        return v.lower()

# Schema for creating a new disaster report
class DisasterReportCreate(BaseModel):
    location: Location
    event: Event
    media: Optional[List[Media]] = Field(
        None,
        description="Optional list of media files related to the disaster"
    )

    class Config:
        schema_extra = {
            "example": {
                "location": {
                    "latitude": 37.7749,
                    "longitude": -122.4194,
                    "address": "123 Main Street, San Francisco, CA 94105"
                },
                "event": {
                    "type": "flood",
                    "severity": "severe",
                    "description": "Flash flooding affecting downtown area with water levels rising rapidly"
                },
                "media": [
                    {
                        "type": "image",
                        "url": "https://example.com/flood.jpg",
                        "description": "Flooding in downtown area"
                    }
                ]
            }
        }

# Schema for the complete disaster report (including database fields)
class DisasterReport(DisasterReportCreate):
    report_id: str = Field(..., description="Unique identifier for the report")
    user_id: int = Field(..., description="ID of the user who created the report")
    created_at: datetime = Field(..., description="Timestamp when the report was created")

    class Config:
        orm_mode = True
        schema_extra = {
            "example": {
                "report_id": "123e4567-e89b-12d3-a456-426614174000",
                "user_id": 1234,
                "created_at": "2024-03-19T14:30:00Z",
                "location": {
                    "latitude": 37.7749,
                    "longitude": -122.4194,
                    "address": "123 Main Street, San Francisco, CA 94105"
                },
                "event": {
                    "type": "flood",
                    "severity": "severe",
                    "description": "Flash flooding affecting downtown area with water levels rising rapidly"
                },
                "media": [
                    {
                        "type": "image",
                        "url": "https://example.com/flood.jpg",
                        "description": "Flooding in downtown area"
                    }
                ]
            }
        }

# Schema for API responses
class DisasterReportResponse(BaseModel):
    message: str = Field(..., description="Response message")
    report_id: Optional[str] = Field(None, description="ID of the affected report")

    class Config:
        schema_extra = {
            "example": {
                "message": "Disaster report created successfully",
                "report_id": "123e4567-e89b-12d3-a456-426614174000"
            }
        }

class DisasterReportsListResponse(BaseModel):
    disaster_reports: List[DisasterReport]

    class Config:
        schema_extra = {
            "example": {
                "disaster_reports": [
                    {
                        "report_id": "123e4567-e89b-12d3-a456-426614174000",
                        "user_id": 1234,
                        "created_at": "2024-03-19T14:30:00Z",
                        "location": {
                            "latitude": 37.7749,
                            "longitude": -122.4194,
                            "address": "123 Main Street, San Francisco, CA 94105"
                        },
                        "event": {
                            "type": "flood",
                            "severity": "severe",
                            "description": "Flash flooding affecting downtown area"
                        },
                        "media": [
                            {
                                "type": "image",
                                "url": "https://example.com/flood.jpg",
                                "description": "Flooding in downtown area"
                            }
                        ]
                    }
                ]
            }
        }
