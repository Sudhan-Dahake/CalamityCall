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

if __name__ == '__main__':
    unittest.main()
