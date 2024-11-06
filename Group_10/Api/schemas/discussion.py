from pydantic import BaseModel
from typing import Optional

class TopicCreate(BaseModel):
    user_id: int
    title: str
    description: Optional[str] = None

class PostCreate(BaseModel):
    user_id: int
    topic_id: int
    content: str
    image_url: Optional[str] = None
