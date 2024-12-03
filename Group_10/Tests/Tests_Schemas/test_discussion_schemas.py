import unittest
from Group_10.Api.schemas import TopicCreate, PostCreate

class TestDiscussionSchemas(unittest.TestCase):

    def test_topic_create_schema(self):
        topic = TopicCreate(user_id=1, title="Test Topic", description="This is a test topic")
        self.assertEqual(topic.user_id, 1)
        self.assertEqual(topic.title, "Test Topic")
        self.assertEqual(topic.description, "This is a test topic")

        # Test optional description
        topic_no_desc = TopicCreate(user_id=2, title="Another Test Topic")
        self.assertEqual(topic_no_desc.user_id, 2)
        self.assertEqual(topic_no_desc.title, "Another Test Topic")
        self.assertIsNone(topic_no_desc.description)

    def test_post_create_schema(self):
        post = PostCreate(user_id=1, topic_id=1, content="This is a test post", image_url="https://example.com/image.jpg")
        self.assertEqual(post.user_id, 1)
        self.assertEqual(post.topic_id, 1)
        self.assertEqual(post.content, "This is a test post")
        self.assertEqual(post.image_url, "https://example.com/image.jpg")

        # Test optional image_url
        post_no_image = PostCreate(user_id=2, topic_id=1, content="Another test post")
        self.assertEqual(post_no_image.user_id, 2)
        self.assertEqual(post_no_image.topic_id, 1)
        self.assertEqual(post_no_image.content, "Another test post")
        self.assertIsNone(post_no_image.image_url)

    # Edge Test Cases
    def test_topic_create_schema_empty_title(self):
        with self.assertRaises(ValidationError):
            TopicCreate(user_id=1, title="")
        
    def test_topic_create_schema_long_title_and_description(self):
        long_title = "A" * 1000
        long_desc = "B" * 5000
        with self.assertRaises(ValidationError):
            TopicCreate(user_id=1, title=long_title, description=long_desc)

    def test_topic_create_schema_negative_user_id(self):
        with self.assertRaises(ValidationError):
            TopicCreate(user_id=-1, title="Valid Title")

    def test_post_create_schema_empty_content(self):
        with self.assertRaises(ValidationError):
            PostCreate(user_id=1, topic_id=1, content="")
        
    def test_post_create_schema_long_content(self):
        long_content = "A" * 10000
        with self.assertRaises(ValidationError):
            PostCreate(user_id=1, topic_id=1, content=long_content)

    def test_post_create_schema_invalid_image_url(self):
        with self.assertRaises(ValidationError):
            PostCreate(user_id=1, topic_id=1, content="Valid", image_url="not_a_url")

    def test_post_create_schema_negative_user_and_topic_ids(self):
        with self.assertRaises(ValidationError):
            PostCreate(user_id=-1, topic_id=-1, content="Valid")

if __name__ == '__main__':
    unittest.main()