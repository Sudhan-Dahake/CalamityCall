import unittest
import json
from jsonschema import validate

class TestPostJSON(unittest.TestCase):
    def setUp(self):
        with open('post.json', 'r') as f:
            self.post = json.load(f)
        
        self.schema = {
            "type": "object",
            "properties": {
                "post_id": {"type": "string"},
                "topic_id": {"type": "string"},
                "user_id": {"type": "string"},
                "content": {"type": "string", "maxLength": 4000},
                "created_at": {"type": "string", "format": "date-time"},
                "media": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "type": {"type": "string", "enum": ["image", "video", "audio"]},
                            "url": {"type": "string", "format": "uri"},
                            "description": {"type": "string", "maxLength": 250}
                        },
                        "required": ["type", "url"]
                    }
                },
                "reactions": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "user_id": {"type": "string"},
                            "post_id": {"type": "string"},
                            "reaction_type": {"type": "string"}
                        },
                        "required": ["user_id", "post_id", "reaction_type"]
                    }
                }
            },
            "required": ["post_id", "topic_id", "user_id", 
                         "content", "created_at"]
        }

    def test_json_structure(self):
        try:
            validate(instance=self.post, schema=self.schema)
        except jsonschema.exceptions.ValidationError as ve:
            self.fail(f"JSON does not match the schema: {ve}")

    def test_post_id_is_string(self):
        self.assertIsInstance(self.post['post_id'], str)

    def test_topic_id_is_string(self):
        self.assertIsInstance(self.post['topic_id'], str)

    def test_user_id_is_string(self):
        self.assertIsInstance(self.post['user_id'], str)

    def test_content_max_length(self):
        self.assertLessEqual(len(self.post['content']), 4000)

    def test_created_at_is_iso8601(self):
        from dateutil.parser import parse
        try:
            parse(self.post['created_at'])
        except ValueError:
            self.fail("created_at is not a valid ISO-8601 timestamp")

    # Edge Test Cases
    def test_post_json_empty_content(self):
        invalid_post = self.post.copy()
        invalid_post['content'] = ""
        with self.assertRaises(ValidationError):
            validate(instance=invalid_post, schema=self.schema)

    def test_post_json_long_content(self):
        long_content = 'A' * 4001  # Exceeding max length
        invalid_post = self.post.copy()
        invalid_post['content'] = long_content
        with self.assertRaises(ValidationError):
            validate(instance=invalid_post, schema=self.schema)

    def test_post_json_invalid_image_url(self):
        invalid_post = self.post.copy()
        invalid_post['media'] = [{
            'type': 'image',
            'url': 'not_a_url',  # Invalid URL
            'description': 'Test image'
        }]
        with self.assertRaises(ValidationError):
            validate(instance=invalid_post, schema=self.schema)

if __name__ == '__main__':
    unittest.main()
