from fastapi import APIRouter, HTTPException, status
from typing import List, Optional
from datetime import datetime, timezone
# Import your Supabase models
from ...supabase_db.models.topics import TopicModel
from ...supabase_db.models.posts import PostModel

# Import Pydantic schemas
from ..schemas.forum_schemas import (
    TopicCreateRequest, 
    TopicResponse, 
    PostCreateRequest, 
    PostResponse
)


# Initialize the APIRouter instance
forum_router = APIRouter()

# Initialize models
topic_model = TopicModel()
post_model = PostModel()



@forum_router.post("/topics", response_model=TopicResponse)
async def create_topic(topic: TopicCreateRequest):
    try:
        created_topic = topic_model.CreateTopic(
            user_id=topic.user_id,
            title=topic.title,
            description=topic.description or "",
            created_at=datetime.now(timezone.utc).replace(microsecond=0)  # Remove microseconds
        )

        if not created_topic:
            raise HTTPException(status_code=500, detail="Failed to create topic")

        # Convert to response model
        return TopicResponse(
            topic_id=created_topic['topic_id'],
            user_id=created_topic['user_id'],
            title=created_topic['title'],
            description=created_topic.get('description', ''),
            created_at=created_topic['created_at']  # Ensure it's in ISO 8601 format without microseconds
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")



@forum_router.post("/posts", response_model=PostResponse)
async def create_post(post: PostCreateRequest):
    """
    Create a new post within a topic
    """
    try:
        post_data = {
            "user_id": post.user_id,
            "topic_id": post.topic_id,
            "content": post.content,
            "image_url": post.image_url,
            "created_at": datetime.now(timezone.utc),  # Updated to timezone-aware datetime
        }
        created_post = post_model.CreatePost(**post_data)
        if not created_post:
            raise HTTPException(status_code=500, detail="Failed to create post")

        return PostResponse(**created_post)

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")

@forum_router.get("/topics", response_model=List[TopicResponse])
async def read_topics(user_id: Optional[int] = None):
    """
    Retrieve topics, optionally filtered by user_id
    """
    try:
        topics = topic_model.ReadTopic(user_id=user_id)
        if not topics:
            raise HTTPException(status_code=404, detail="No topics found")

        return [TopicResponse(**topic) for topic in topics]

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")

@forum_router.get("/posts", response_model=List[PostResponse])
async def read_posts(topic_id: Optional[int] = None, user_id: Optional[int] = None):
    """
    Retrieve posts, optionally filtered by topic_id or user_id
    """
    try:
        posts = post_model.ReadPost(topic_id=topic_id, user_id=user_id)
        if not posts:
            raise HTTPException(status_code=404, detail="No posts found")

        return [PostResponse(**post) for post in posts]

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")
