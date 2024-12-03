import unittest
import json
from jsonschema import validate

class TestTopicJSON(unittest.TestCase):
    def setUp(self):
        with open('topic.json', 'r') as f:
            self.topic = json.load(f)
        
        self.schema = {
            "type": "object",
            "properties": {
                "topic_id": {"type": "string"},
                "user_id": {"type": "string"},
                "created_at": {"type": "string", "format": "date-time"},
                "title": {"type": "string", "maxLength": 100},
                "description": {"type": ["string", null], 
                                'maxLength': 250},  # Optional field
                "posts": {
                    "type": 'array',
                    'items': {
                        'type': 'object',  # You can define the post structure here if needed
                        # Define properties of post if necessary, or leave empty for now.
                    }
                }
            },
            # Required fields for topic
            'required': ['topic_id', 'user_id', 'created_at', 'title']
        }

    def test_json_structure(self):
        try:
            validate(instance=self.topic, schema=self.schema)
        except jsonschema.exceptions.ValidationError as ve:
            self.fail(f"JSON does not match the schema: {ve}")

    def test_topic_id_is_string(self):
        self.assertIsInstance(self.topic['topic_id'], str)

    def test_user_id_is_string(self):
        self.assertIsInstance(self.topic['user_id'], str)

    def test_created_at_is_iso8601(self):
        from dateutil.parser import parse
        try:
            parse(self.topic['created_at'])
        except ValueError:
            self.fail("created_at is not a valid ISO-8601 timestamp")

    def test_title_max_length(self):
        self.assertLessEqual(len(self.topic['title']), 100)

    def test_description_max_length(self):
        if 'description' in self.topic and self.topic['description'] is not None:
            self.assertLessEqual(len(self.topic['description']), 250)

    # Edge Test Cases
    def test_topic_json_empty_title(self):
        invalid_topic = self.topic.copy()
        invalid_topic['title'] = ""
        with self.assertRaises(ValidationError):
            validate(instance=invalid_topic, schema=self.schema)

    def test_topic_json_long_title_and_description(self):
        long_title = 'A' * 101  # Exceeding max length for title
        long_desc = 'B' * 251   # Exceeding max length for description

        invalid_topic = self.topic.copy()
        
        invalid_topic['title'] = long_title
        with self.assertRaises(ValidationError):
            validate(instance=invalid_topic, schema=self.schema)

        invalid_topic['title'] = 'Valid Title'
        invalid_topic['description'] = long_desc
        with self.assertRaises(ValidationError):
            validate(instance=invalid_topic, schema=self.schema)

if __name__ == '__main__':
    unittest.main()
