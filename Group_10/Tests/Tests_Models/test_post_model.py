import unittest
from unittest.mock import patch, MagicMock

from Group_10 import PostModel

class TestPostModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.posts.create_client')
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.post_model = PostModel()

    def test_create_post_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{"post_id": 1}])
        response = self.post_model.CreatePost(1, 1, "Test post")
        self.assertIsNotNone(response)
        self.assertEqual(response['post_id'], 1)

    def test_create_post_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        response = self.post_model.CreatePost(1, 1, "Test post")
        self.assertIsNone(response)

    def test_read_post_success(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{"post_id": 1}])
        response = self.post_model.ReadPost(post_id=1)
        self.assertIsNotNone(response)
        self.assertEqual(response[0]['post_id'], 1)

    def test_read_post_failure(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        response = self.post_model.ReadPost(post_id=99)
        self.assertIsNone(response)

    def test_update_post_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.UpdatePost(1, content="Updated content")
        self.assertTrue(result)

    def test_update_post_failure(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.post_model.UpdatePost(1, content="Updated content")
        self.assertFalse(result)

    def test_delete_post_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.DeletePost(1)
        self.assertTrue(result)

    def test_delete_post_failure(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=400)
        result = self.post_model.DeletePost(99)
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()
