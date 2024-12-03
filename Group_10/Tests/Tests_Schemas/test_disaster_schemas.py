import unittest
from datetime import datetime
from Group_10.Api.schemas import (
    Media,
    Location,
    Event,
    DisasterReportCreate,
    DisasterReport,
    DisasterReportResponse,
    DisasterReportsListResponse
)

class TestDisasterSchemas(unittest.TestCase):

    def test_media_schema(self):
        media = Media(type="image", url="https://example.com/image.jpg", description="Test image")
        self.assertEqual(media.type, "image")
        self.assertEqual(media.url, "https://example.com/image.jpg")
        self.assertEqual(media.description, "Test image")

        with self.assertRaises(ValueError):
            Media(type="invalid", url="https://example.com/image.jpg")

    def test_location_schema(self):
        location = Location(latitude=37.7749, longitude=-122.4194, address="123 Test St, City, State 12345")
        self.assertEqual(location.latitude, 37.774900)
        self.assertEqual(location.longitude, -122.419400)
        self.assertEqual(location.address, "123 Test St, City, State 12345")

        with self.assertRaises(ValueError):
            Location(latitude=91, longitude=-122.4194, address="Invalid latitude")

    def test_event_schema(self):
        event = Event(type="flood", severity="severe", description="Test flood description")
        self.assertEqual(event.type, "flood")
        self.assertEqual(event.severity, "severe")
        self.assertEqual(event.description, "Test flood description")

        with self.assertRaises(ValueError):
            Event(type="invalid", severity="severe", description="Invalid event type")

    def test_disaster_report_create_schema(self):
        report = DisasterReportCreate(
            location=Location(latitude=37.7749, longitude=-122.4194, address="123 Test St, City, State 12345"),
            event=Event(type="flood", severity="severe", description="Test flood description"),
            media=[Media(type="image", url="https://example.com/image.jpg", description="Test image")]
        )
        self.assertIsInstance(report.location, Location)
        self.assertIsInstance(report.event, Event)
        self.assertIsInstance(report.media[0], Media)

    def test_disaster_report_schema(self):
        report = DisasterReport(
            report_id="test123",
            user_id=1,
            created_at=datetime.now(),
            location=Location(latitude=37.7749, longitude=-122.4194, address="123 Test St, City, State 12345"),
            event=Event(type="flood", severity="severe", description="Test flood description"),
            media=[Media(type="image", url="https://example.com/image.jpg", description="Test image")]
        )
        self.assertEqual(report.report_id, "test123")
        self.assertEqual(report.user_id, 1)
        self.assertIsInstance(report.created_at, datetime)

    def test_disaster_report_response_schema(self):
        response = DisasterReportResponse(message="Test message", report_id="test123")
        self.assertEqual(response.message, "Test message")
        self.assertEqual(response.report_id, "test123")

    def test_disaster_reports_list_response_schema(self):
        report = DisasterReport(
            report_id="test123",
            user_id=1,
            created_at=datetime.now(),
            location=Location(latitude=37.7749, longitude=-122.4194, address="123 Test St, City, State 12345"),
            event=Event(type="flood", severity="severe", description="Test flood description"),
            media=[Media(type="image", url="https://example.com/image.jpg", description="Test image")]
        )
        response = DisasterReportsListResponse(disaster_reports=[report])
        self.assertEqual(len(response.disaster_reports), 1)
        self.assertIsInstance(response.disaster_reports[0], DisasterReport)

    # Edge Test Cases
    def test_media_schema_invalid_type(self):
        with self.assertRaises(ValidationError):
            Media(type="invalid", url="https://example.com/image.jpg")

    def test_location_schema_invalid_latitude(self):
        with self.assertRaises(ValidationError):
            Location(latitude=91, longitude=-122.4194, address="Invalid latitude")

    def test_event_schema_invalid_type(self):
        with self.assertRaises(ValidationError):
            Event(type="invalid", severity="severe", description="Invalid event type")

    def test_disaster_report_create_schema_invalid_media_type(self):
        with self.assertRaises(ValidationError):
            DisasterReportCreate(
                location=Location(latitude=37.7749, longitude=-122.4194, address="123 Test St"),
                event=Event(type="flood", severity="severe", description="Test flood description"),
                media=[Media(type="invalid", url="https://example.com/image.jpg")]
            )

    def test_disaster_report_schema_invalid_report_id(self):
        future_date = datetime.now() + timedelta(days=365)
        with self.assertRaises(ValidationError):
            DisasterReport(
                report_id="",  # Empty report_id
                user_id=1,
                created_at=future_date,  # Future date
                location=Location(latitude=0, longitude=0, address="Valid"),
                event=Event(type="flood", severity="severe", description="Valid")
            )

    def test_disaster_report_response_schema_empty_message(self):
        with self.assertRaises(ValidationError):
            DisasterReportResponse(message="", report_id="valid_id")

    def test_disaster_reports_list_response_schema_empty_list(self):
        empty_response = DisasterReportsListResponse(disaster_reports=[])
        self.assertEqual(len(empty_response.disaster_reports), 0)

    def test_disaster_reports_list_response_schema_large_number_of_reports(self):
        large_list = [DisasterReport(
            report_id=f"test{i}",
            user_id=1,
            created_at=datetime.now(),
            location=Location(latitude=0, longitude=0, address="Valid"),
            event=Event(type="flood", severity="severe", description="Valid")
        ) for i in range(1000)]
        large_response = DisasterReportsListResponse(disaster_reports=large_list)
        self.assertEqual(len(large_response.disaster_reports), 1000)

if __name__ == '__main__':
    unittest.main()
