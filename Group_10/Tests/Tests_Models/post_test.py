import unittest
from unittest.mock import patch, MagicMock
from Group_10.supabase_db.models.posts import PostsModel

class TestPostsModel(unittest.TestCase):
    @patch('Group_10.supabase_db.models.posts.create_client')  # Mock the client creation
    def setUp(self, mock_create_client):
        self.mock_client = MagicMock()
        mock_create_client.return_value = self.mock_client
        self.posts_model = PostsModel()

    def test_create_post_success(self):
        # Mock successful insert response
        self.mock_client.from_().insert().execute.return_value = MagicMock(
            data=[{'post_id': 1}]
        )
        
        # Test CreatePost method
        result = self.posts_model.CreatePost(
            content="Test content", user_id=1, image_url="http://example.com/test.jpg"
        )
        
        self.assertTrue(result)
        self.assertEqual(self.posts_model.post_id, 1)

    def test_create_post_failure(self):
        # Mock failure insert response
        self.mock_client.from_().insert().execute.return_value = MagicMock(data=None)

        # Test CreatePost method
        result = self.posts_model.CreatePost(content="Test content", user_id=1)
        
        self.assertFalse(result)

if __name__ == '__main__':
    unittest.main()