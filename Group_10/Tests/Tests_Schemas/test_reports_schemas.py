import unittest
from datetime import datetime
from Group_10.Api.schemas import ReportsDisasterReportCreate

class TestReportsSchemas(unittest.TestCase):

    def test_disaster_report_create_schema(self):
        report = ReportsDisasterReportCreate(
            user_id=1,
            timestamp=datetime.now(),
            latitude=37.7749,
            longitude=-122.4194,
            address="123 Test St, City, State 12345",
            weather_event_type="flood",
            weather_event_severity="severe",
            weather_event_description="Test flood description"
        )
        self.assertEqual(report.user_id, 1)
        self.assertIsInstance(report.timestamp, datetime)
        self.assertEqual(report.latitude, 37.7749)
        self.assertEqual(report.longitude, -122.4194)
        self.assertEqual(report.address, "123 Test St, City, State 12345")
        self.assertEqual(report.weather_event_type, "flood")
        self.assertEqual(report.weather_event_severity, "severe")
        self.assertEqual(report.weather_event_description, "Test flood description")

    # Edge Test Cases
    def test_disaster_report_create_schema_valid_data(self):
        report = ReportsDisasterReportCreate(
            user_id=1,
            timestamp=datetime.now(),
            latitude=37.7749,
            longitude=-122.4194,
            address="123 Test St",
            weather_event_type="flood",
            weather_event_severity="severe",
            weather_event_description="Test flood description"
        )
        self.assertEqual(report.user_id, 1)
        
    def test_disaster_report_create_schema_future_timestamp(self): 
        future_date = datetime.now() + timedelta(days=365)
        
        # Test with future timestamp
        with self.assertRaises(ValidationError): 
           ReportsDisasterReportCreate(
               user_id=1,
               timestamp=future_date,
               latitude=0,
               longitude=0,
               address="Valid Address",
               weather_event_type="flood",
               weather_event_severity="severe",
               weather_event_description="Valid description"
           )

     # Test invalid latitude and longitude 
    def test_disaster_report_create_schema_invalid_coordinates(self): 
        with self.assertRaises(ValidationError): 
            ReportsDisasterReportCreate(
                user_id=1,
                timestamp=datetime.now(),
                latitude=91,
                longitude=-181,
                address="Valid Address",
                weather_event_type="flood",
                weather_event_severity="severe",
                weather_event_description="Valid description"
            )

    # Test empty address 
    def test_disaster_report_create_schema_empty_address(self): 
        with self.assertRaises(ValidationError): 
            ReportsDisasterReportCreate(
                user_id=1,
                timestamp=datetime.now(),
                latitude=0,
                longitude=0,
                address="", 
                weather_event_type="flood",
                weather_event_severity="severe",
                weather_event_description="Valid description"
            )

    # Test invalid weather event type and severity 
    def test_disaster_report_create_schema_invalid_event_details(self): 
        with self.assertRaises(ValidationError): 
            ReportsDisasterReportCreate(
                user_id=1,
                timestamp=datetime.now(),
                latitude=0,
                longitude=0,
                address="Valid Address",
                weather_event_type="",  
                weather_event_severity="",  
                weather_event_description=""
            )

if __name__ == '__main__':
    unittest.main()