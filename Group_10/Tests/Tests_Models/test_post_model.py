import unittest
from unittest.mock import patch, MagicMock

from Group_10 import PostModel

class TestPostModel(unittest.TestCase):

    @patch('Group_10.supabase_db.models.posts.create_client')  # Ensure the correct module path
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.post_model = PostModel()
   
    def test_create_post_success(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=[{'post_id': 1}])
        result = self.post_model.CreatePost(
            user_id=123,
            topic_id=10,
            content="This is a test post",
            #image_url="https://example.com/image.jpg"
        )
        self.assertIsNotNone(result)
        self.assertEqual(result['post_id'], 1)

    def test_create_post_failure(self):
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)
        result = self.post_model.CreatePost(
            user_id=123,
            topic_id=10,
            content="This is a test post"
            #image_url="https://example.com/image.jpg"
        )
        self.assertIsNone(result)

    def test_read_post_by_user_id(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=[{'post_id': 1}])
        result = self.post_model.ReadPost(user_id=123)
        self.assertIsNotNone(result)
        self.assertEqual(result[0]['post_id'], 1)

    def test_read_post_by_user_id_failure(self):
        self.mock_client.from_().select().eq().execute.return_value = MagicMock(data=None)
        result = self.post_model.ReadPost(user_id=123)
        self.assertIsNone(result)

    def test_update_post_success(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.UpdatePost(
            post_id=1,
            content="Updated content",
            #image_url="https://example.com/updated_image.jpg"
        )
        self.assertTrue(result)

    def test_update_post_failure(self):
        self.mock_client.from_().update().eq().execute.return_value = MagicMock(status_code=400)
        result = self.post_model.UpdatePost(
            post_id=1,
            content="Updated content"
            #image_url="https://example.com/updated_image.jpg"
        )
        self.assertFalse(result)

    def test_delete_post_success(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=204)
        result = self.post_model.DeletePost(post_id=1)
        self.assertTrue(result)

    def test_delete_post_failure(self):
        self.mock_client.from_().delete().eq().execute.return_value = MagicMock(status_code=400)
        result = self.post_model.DeletePost(post_id=1)
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()
