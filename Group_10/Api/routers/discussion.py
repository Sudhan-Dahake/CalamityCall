from fastapi import APIRouter, Depends
from ..schemas.discussion import TopicCreate, PostCreate
from ...supabase_db.models import TopicsModel, PostsModel

router = APIRouter()

@router.post("/topics/")
def create_topic(topic: TopicCreate):
    topic_model = TopicsModel()
    return topic_model.CreateTopic(**topic.dict())

@router.get("/topics/{topic_id}")
def read_topic(topic_id: int):
    topic_model = TopicsModel()
    return topic_model.ReadTopic(topic_id=topic_id)
