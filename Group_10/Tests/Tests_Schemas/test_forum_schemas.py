import unittest
from datetime import datetime
from Group_10.Api.schemas import (
    MediaItem,
    ReactionItem,
    TopicCreateRequest,
    TopicResponse,
    PostCreateRequest,
    PostResponse
)

class TestForumSchemas(unittest.TestCase):

    def test_media_item_schema(self):
        media = MediaItem(type="image", url="https://example.com/image.jpg", description="Test image")
        self.assertEqual(media.type, "image")
        self.assertEqual(media.url, "https://example.com/image.jpg")
        self.assertEqual(media.description, "Test image")

        # Test optional description
        media_no_desc = MediaItem(type="video", url="https://example.com/video.mp4")
        self.assertEqual(media_no_desc.type, "video")
        self.assertEqual(media_no_desc.url, "https://example.com/video.mp4")
        self.assertIsNone(media_no_desc.description)

    def test_reaction_item_schema(self):
        reaction = ReactionItem(user_id=1, reaction_type="like")
        self.assertEqual(reaction.user_id, 1)
        self.assertEqual(reaction.reaction_type, "like")

    def test_topic_create_request_schema(self):
        topic = TopicCreateRequest(user_id=1, title="Test Topic", description="This is a test topic")
        self.assertEqual(topic.user_id, 1)
        self.assertEqual(topic.title, "Test Topic")
        self.assertEqual(topic.description, "This is a test topic")

        # Test optional description
        topic_no_desc = TopicCreateRequest(user_id=2, title="Another Test Topic")
        self.assertEqual(topic_no_desc.user_id, 2)
        self.assertEqual(topic_no_desc.title, "Another Test Topic")
        self.assertIsNone(topic_no_desc.description)

    def test_topic_response_schema(self):
        topic = TopicResponse(
            topic_id=1,
            user_id=1,
            title="Test Topic",
            description="This is a test topic",
            created_at=datetime.now()
        )
        self.assertEqual(topic.topic_id, 1)
        self.assertEqual(topic.user_id, 1)
        self.assertEqual(topic.title, "Test Topic")
        self.assertEqual(topic.description, "This is a test topic")
        self.assertIsInstance(topic.created_at, datetime)

    def test_post_create_request_schema(self):
        post = PostCreateRequest(user_id=1, topic_id=1, content="This is a test post", image_url="https://example.com/image.jpg")
        self.assertEqual(post.user_id, 1)
        self.assertEqual(post.topic_id, 1)
        self.assertEqual(post.content, "This is a test post")
        self.assertEqual(post.image_url, "https://example.com/image.jpg")

        # Test optional image_url
        post_no_image = PostCreateRequest(user_id=2, topic_id=1, content="Another test post")
        self.assertEqual(post_no_image.user_id, 2)
        self.assertEqual(post_no_image.topic_id, 1)
        self.assertEqual(post_no_image.content, "Another test post")
        self.assertIsNone(post_no_image.image_url)

    def test_post_response_schema(self):
        post = PostResponse(
            post_id=1,
            user_id=1,
            topic_id=1,
            content="This is a test post",
            image_url="https://example.com/image.jpg",
            created_at=datetime.now()
        )
        self.assertEqual(post.post_id, 1)
        self.assertEqual(post.user_id, 1)
        self.assertEqual(post.topic_id, 1)
        self.assertEqual(post.content, "This is a test post")
        self.assertEqual(post.image_url, "https://example.com/image.jpg")
        self.assertIsInstance(post.created_at, datetime)

    # Edge Test Cases
    def test_media_item_schema_empty_type(self):
        with self.assertRaises(ValidationError):
            MediaItem(type="", url="https://example.com/image.jpg")
        
    def test_media_item_schema_empty_url(self):
        with self.assertRaises(ValidationError):
            MediaItem(type="image", url="")
        
    def test_media_item_schema_long_description(self):
        long_desc = "a" * 251
        with self.assertRaises(ValidationError):
            MediaItem(type="image", url="https://example.com/image.jpg", description=long_desc)

    def test_reaction_item_schema_negative_user_id(self):
        with self.assertRaises(ValidationError):
            ReactionItem(user_id=-1, reaction_type="like")
        
    def test_reaction_item_schema_empty_reaction_type(self):
        with self.assertRaises(ValidationError):
            ReactionItem(user_id=1, reaction_type="")
    
    def test_topic_create_request_schema_negative_user_id(self):
        with self.assertRaises(ValidationError):
            TopicCreateRequest(user_id=-1, title="Valid Title")
        
    def test_topic_create_request_schema_empty_title(self):
        with self.assertRaises(ValidationError):
            TopicCreateRequest(user_id=1, title="")
        
    def test_topic_create_request_schema_long_title_and_description(self):
        long_title = "a" * 101
        long_desc = "b" * 251
        with self.assertRaises(ValidationError):
            TopicCreateRequest(user_id=1, title=long_title, description=long_desc)

    def test_topic_response_schema_future_date(self):
        future_date = datetime.now() + timedelta(days=365)
        with self.assertRaises(ValidationError):
            TopicResponse(
                topic_id=-1,
                user_id=1,
                title="Valid Title",
                description="Valid Description",
                created_at=future_date
            )

    def test_post_create_request_schema_negative_user_and_topic_ids(self):
        with self.assertRaises(ValidationError):
            PostCreateRequest(user_id=-1, topic_id=-1, content="Valid Content")
        
    def test_post_create_request_schema_empty_content(self):
        with self.assertRaises(ValidationError):
            PostCreateRequest(user_id=1, topic_id=1, content="")
        
    def test_post_create_request_schema_long_content(self):
        long_content = "a" * 4001
        with self.assertRaises(ValidationError):
            PostCreateRequest(user_id=1, topic_id=1, content=long_content)

    def test_post_response_schema_future_date(self):
        future_date = datetime.now() + timedelta(days=365)
        with self.assertRaises(ValidationError):
            PostResponse(
                post_id=-1,
                user_id=1,
                topic_id=1,
                content="Valid Content",
                image_url="https://example.com/image.jpg",
                created_at=future_date
            )

if __name__ == '__main__':
    unittest.main()