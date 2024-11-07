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
            result = self.topic_model.CreateTopic(
                user_id=123,
                title="Disaster Preparedness",
                description="Discussion on preparing for disasters"
            )
            self.assertIsNotNone(result)
            self.assertEqual(result['topic_id'], 1)

    def test_create_topic_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        result = self.topic_model.CreateTopic(
            user_id=123,
            title="",  # Intentionally invalid to trigger a failure
            description="Discussion on preparing for disasters"
        )
        self.assertIsNone(result)

    def test_read_topic_by_user_id(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{'topic_id': 1}])
        result = self.topic_model.ReadTopic(user_id=123)
        self.assertIsNotNone(result)
        self.assertEqual(result[0]['topic_id'], 1)

    def test_read_topic_by_user_id_failure(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        result = self.topic_model.ReadTopic(user_id=999)  # Non-existent user ID
        self.assertIsNone(result)

    def test_update_topic_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.topic_model.UpdateTopic(
            topic_id=1,
            title="Updated Title",
            description="Updated Description"
        )
        self.assertTrue(result)

    def test_update_topic_failure(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.topic_model.UpdateTopic(
            topic_id=1,
            title="Updated Title",
            description="Updated Description"
        )
        self.assertFalse(result)

    def test_delete_topic_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.topic_model.DeleteTopic(topic_id=1)
        self.assertTrue(result)

    def test_delete_topic_failure(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=404)
        result = self.topic_model.DeleteTopic(topic_id=999)  # Non-existent topic ID
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()
