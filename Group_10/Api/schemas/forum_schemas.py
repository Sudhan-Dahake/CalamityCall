from typing import List, Optional
from pydantic import BaseModel, Field
from datetime import datetime

class MediaItem(BaseModel):
    type: str = Field(..., description="Type of media (image, video, or audio)", example="image")
    url: str = Field(..., description="URL of the media file", example="https://example.com/image.jpg")
    description: Optional[str] = Field(None, max_length=250, description="Description of media content", example="A beautiful sunset")

class ReactionItem(BaseModel):
    user_id: int = Field(..., description="User ID who reacted", example=1)
    reaction_type: str = Field(..., description="Type of reaction", example="like")

class TopicCreateRequest(BaseModel):
    user_id: int = Field(..., description="ID of user who created the topic", example=1)
    title: str = Field(..., max_length=100, description="Topic title", example="Discussion about climate change")
    description: Optional[str] = Field(None, max_length=250, description="Topic description", example="This topic is for discussing the impacts of climate change.")

class TopicResponse(TopicCreateRequest):
    topic_id: int
    created_at: datetime

    class Config:
        orm_mode = True

class PostCreateRequest(BaseModel):
    user_id: int = Field(..., description="User ID of post creator", example=2)
    topic_id: int = Field(..., description="Topic ID for the post", example=5)
    content: str = Field(..., max_length=4000, description="Post content", example="This is a test post.")
    image_url: Optional[str] = Field(None, example="https://example.com/image.jpg")

class PostResponse(PostCreateRequest):
    post_id: int
    created_at: datetime

    class Config:
        orm_mode = True
