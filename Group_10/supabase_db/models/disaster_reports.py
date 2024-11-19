import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()

class DisasterReportsModel:
    def __init__(self, tableName: str = "disaster_reports"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreateReport(self, report_id, user_id, timestamp, latitude, longitude, address, weather_event_type, 
                     weather_event_severity, weather_event_description, media=None):
        # Format data for insertion
        report_data = {
            "report_id": report_id,
            "user_id": user_id,
            "created_at": timestamp,
            "latitude": latitude,
            "longitude": longitude,
            "address": address,
            "weather_event_type": weather_event_type,
            "weather_event_severity": weather_event_severity,
            "weather_event_description": weather_event_description,
        }

        # Optional media field
        if media:
            report_data["media"] = media

        # Insert data into Supabase
        response = self.client.from_(self.tableName).insert(report_data).execute()
        if response.data:
            print("Disaster report created successfully.")
            return response.data[0]
        else:
            print(f"Error creating report: {response}")
            return None

    def ReadReport(self, report_id=None, user_id=None):
        query = self.client.from_(self.tableName).select("*")
        if report_id:
            query = query.eq("report_id", report_id)
        if user_id:
            query = query.eq("user_id", user_id)

        response = query.execute()
        if response.data:
            return response.data
        else:
            print(f"No reports found or error: {response}")
            return None

    def UpdateReport(self, report_id, latitude=None, longitude=None, address=None, weather_event_type=None, 
                     weather_event_severity=None, weather_event_description=None):
        updated_fields = {}
        if latitude is not None:
            updated_fields["latitude"] = latitude
        if longitude is not None:
            updated_fields["longitude"] = longitude
        if address is not None:
            updated_fields["address"] = address
        if weather_event_type is not None:
            updated_fields["weather_event_type"] = weather_event_type
        if weather_event_severity is not None:
            updated_fields["weather_event_severity"] = weather_event_severity
        if weather_event_description is not None:
            updated_fields["weather_event_description"] = weather_event_description

        response = self.client.from_(self.tableName).update(updated_fields).eq("report_id", report_id).execute()

        if response.status_code == 204:
            print(f"Report {report_id} updated successfully.")
            return True
        else:
            print(f"Error updating report: {response}")
            return False

    def DeleteReport(self, report_id):
        response = self.client.from_(self.tableName).delete().eq("report_id", report_id).execute()

        if response.status_code == 204:
            print(f"Report {report_id} deleted successfully.")
            return True
        else:
            print(f"Error deleting report: {response}")
            return False