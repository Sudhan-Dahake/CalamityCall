import unittest
from unittest.mock import patch, MagicMock
from posts import PostModel

class TestPostModel(unittest.TestCase):

    @patch('posts.create_client')  # Ensure the correct module path
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.post_model = PostModel()

    def test_create_post_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{'post_id': 1}])
        result = self.post_model.CreatePost(content="Test Content", user_id=1)
        self.assertIsNotNone(result)
        self.assertEqual(result['post_id'], 1)

    def test_create_post_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        result = self.post_model.CreatePost(content="Test Content", user_id=1)
        self.assertIsNone(result)

    def test_update_post_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.UpdatePost(post_id=1, content="Updated Content")
        self.assertTrue(result)

    def test_delete_post_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.DeletePost(post_id=1)
        self.assertTrue(result)

if __name__ == '__main__':
    unittest.main()
