import unittest
import json
from jsonschema import validate

class TestDisasterReportJSON(unittest.TestCase):
    def setUp(self):
        with open('disaster-report.json', 'r') as f:
            self.disaster_report = json.load(f)
        
        self.schema = {
            "type": "object",
            "properties": {
                "report_id": {"type": "string"},
                "user_id": {"type": "string"},
                "created_at": {"type": "string", "format": "date-time"},
                "location": {
                    "type": "object",
                    "properties": {
                        "latitude": {"type": "number"},
                        "longitude": {"type": "number"},
                        "address": {"type": "string"}
                    },
                    "required": ["latitude", "longitude", "address"]
                },
                "event": {
                    "type": "object",
                    "properties": {
                        "type": {"type": "string"},
                        "severity": {"type": "string", "enum": ["moderate", "severe", "extreme"]},
                        "description": {"type": "string"}
                    },
                    "required": ["type", "severity", "description"]
                },
                "media": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "type": {"type": "string", "enum": ["image", "video", "audio"]},
                            "url": {"type": "string", "format": "uri"},
                            "description": {"type": "string"}
                        },
                        "required": ["type", "url"]
                    }
                }
            },
            "required": ["report_id", "user_id", "created_at", "location", "event"]
        }

    def test_json_structure(self):
        try:
            validate(instance=self.disaster_report, schema=self.schema)
        except jsonschema.exceptions.ValidationError as ve:
            self.fail(f"JSON does not match the schema: {ve}")

    def test_report_id_is_string(self):
        self.assertIsInstance(self.disaster_report['report_id'], str)

    def test_user_id_is_string(self):
        self.assertIsInstance(self.disaster_report['user_id'], str)

    def test_created_at_is_iso8601(self):
        from dateutil.parser import parse
        try:
            parse(self.disaster_report['created_at'])
        except ValueError:
            self.fail("created_at is not a valid ISO-8601 timestamp")

    def test_location_schema(self):
        location = self.disaster_report['location']
        self.assertIsInstance(location['latitude'], (float, int))
        self.assertIsInstance(location['longitude'], (float, int))
        self.assertIsInstance(location['address'], str)

    def test_event_schema(self):
        event = self.disaster_report['event']
        self.assertIsInstance(event['type'], str)
        self.assertIn(event['severity'], ["moderate", "severe", "extreme"])
        self.assertIsInstance(event['description'], str)

    def test_media_schema(self):
        media_items = self.disaster_report.get('media', [])
        if media_items:
            for media in media_items:
                self.assertIn(media['type'], ["image", "video", "audio"])
                self.assertIsInstance(media['url'], str)
                if 'description' in media:
                    self.assertIsInstance(media['description'], str)

    # Edge Test Cases
    def test_disaster_report_json_empty_report_id(self):
        invalid_report = self.disaster_report.copy()
        invalid_report['report_id'] = ""
        
        with self.assertRaises(ValidationError):
            validate(instance=invalid_report, schema=self.schema)
    
    def test_disaster_report_json_invalid_location_latitude_longitude(self): 
        invalid_report = self.disaster_report.copy()
        invalid_report['location']['latitude'] = 91.0  # Invalid latitude
        
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema) 

        invalid_report['location']['latitude'] = -91.0  # Invalid latitude
        
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema) 

        invalid_report['location']['longitude'] = -181.0  # Invalid longitude
        
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema) 

        invalid_report['location']['longitude'] = 181.0  # Invalid longitude
        
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema)

    def test_disaster_report_json_empty_address(self): 
        invalid_report = self.disaster_report.copy() 
        invalid_report["location"]["address"] = ""  
         
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema) 

    # Test empty event type and severity 
    def test_disaster_event_empty_type_severity_description(self): 
        invalid_event = { 
            'event': {'type': '', 'severity': '', 'description': 'Valid description'}  
        } 

        invalid_report.update(invalid_event) 
        
        with self.assertRaises(ValidationError): 
            validate(instance=invalid_report, schema=self.schema) 

if __name__ == '__main__':
    unittest.main()
