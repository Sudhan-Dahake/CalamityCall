import unittest
from unittest.mock import patch, MagicMock
from Group_10 import TopicModel

class TestTopicModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.topics.create_client')  # Ensure the correct module path
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.topic_model = TopicModel()

    def test_create_topic_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{'topic_id': 1}])
        result = self.topic_model.CreateTopic(title="Test Title", description="Test Description")
        self.assertIsNotNone(result)
        self.assertEqual(result['topic_id'], 1)

    def test_create_topic_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        result = self.topic_model.CreateTopic(title="Test Title", description="Test Description")
        self.assertIsNone(result)

    def test_update_topic_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.topic_model.UpdateTopic(topic_id=1, title="Updated Title")
        self.assertTrue(result)

    # def test_delete_topic_success(self):
    #     self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
    #     result = self.topic_model.DeleteTopic(topic_id=1)
    #     self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()
