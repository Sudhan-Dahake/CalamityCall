# topic_services.py
from fastapi import HTTPException, status
from ..models.topics import TopicsModel

# Initialize TopicsModel
topic_model = TopicsModel()

# Create a new discussion topic
def CreateTopic(title: str, description: str):
    topic = topic_model.CreateTopic(title=title, description=description)
    if not topic:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to create topic")
    return topic

# Update an existing topic
def UpdateTopic(topic_id: int, title: str = None, description: str = None):
    success = topic_model.UpdateTopic(topic_id=topic_id, title=title, description=description)
    if not success:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to update topic")
    return {"message": "Topic updated successfully"}

# Delete a topic
def DeleteTopic(topic_id: int):
    success = topic_model.DeleteTopic(topic_id=topic_id)
    if not success:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to delete topic")
    return {"message": "Topic deleted successfully"}