import unittest
from unittest.mock import patch, MagicMock

from Group_10 import TopicModel

class TestTopicModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.topics.create_client')
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.topic_model = TopicModel()

    def test_create_topic_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{"topic_id": 1}])
        response = self.topic_model.CreateTopic(1, "Test Topic", "Description")
        self.assertIsNotNone(response)
        self.assertEqual(response['topic_id'], 1)

    def test_create_topic_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.topic_model.CreateTopic(1, "Test Topic", "Description")
        self.assertIsNone(response)

    def test_read_topic_success(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{"topic_id": 1}])
        response = self.topic_model.ReadTopic(topic_id=1)
        self.assertIsNotNone(response)
        self.assertEqual(response[0]['topic_id'], 1)

    def test_read_topic_failure(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        response = self.topic_model.ReadTopic(topic_id=99)
        self.assertIsNone(response)

    def test_update_topic_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.topic_model.UpdateTopic(1, title="Updated Title")
        self.assertTrue(result)

    def test_update_topic_failure(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.topic_model.UpdateTopic(1, title="Updated Title")
        self.assertFalse(result)

    def test_delete_topic_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.topic_model.DeleteTopic(1)
        self.assertTrue(result)

    def test_delete_topic_failure(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=400)
        result = self.topic_model.DeleteTopic(99)
        self.assertFalse(result)

    # Edge Test Cases
    # Test creating a topic with duplicate name
    def test_create_topic_duplicate_name(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.topic_model.CreateTopic(user_id=1, title="Duplicate Topic")  # Duplicate name
        self.assertIsNone(response)

    # Test creating a topic with special characters
    def test_create_topic_special_characters(self):
        special_title = "@#$%^&*()_+!<>?~"  # Title with special characters
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{"topic_id": 1}])
        response = self.topic_model.CreateTopic(1, special_title, "Description with special characters")
        self.assertIsNotNone(response)
        self.assertEqual(response['topic_id'], 1)

    # Test reading topics with an invalid filter
    def test_read_topic_invalid_filter(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        response = self.topic_model.ReadTopic(topic_id="invalid_id")  # Invalid ID format
        self.assertIsNone(response)

    # Test updating a topic with empty title
    def test_update_topic_empty_title(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.topic_model.UpdateTopic(1, title="")  # Empty title
        self.assertFalse(result)

    # Test deleting a topic that is referenced by other objects (e.g., posts)
    def test_delete_topic_with_dependencies(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=409)
        result = self.topic_model.DeleteTopic(1)  # Topic with dependencies
        self.assertFalse(result)

    

if __name__ == '__main__':
    unittest.main()
