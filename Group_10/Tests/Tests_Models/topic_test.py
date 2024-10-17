import unittest
from unittest.mock import patch, MagicMock
from Group_10.supabase_db.models.topics import TopicsModel 

class TestTopicsModel(unittest.TestCase):
    @patch('Group_10.supabase_db.models.topics.create_client')
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.topics_model = TopicsModel()

    def test_create_topic_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(
            data=[{'topic_id': 1}]
        )
        
        result = self.topics_model.CreateTopic(
            title="Test Topic", description="Test Description"
        )
        
        self.assertTrue(result)
        self.assertEqual(self.topics_model.topic_id, 1)

    def test_create_topic_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        result = self.topics_model.CreateTopic(
            title="Test Topic", description="Test Description"
        )
        
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()
