# from fastapi import HTTPException, status
# from ..models.topics import TopicModel

# topic_model = TopicModel()

# def create_topic(user_id: int, title: str, description: str = ""):
#     topic = topic_model.CreateTopic(user_id=user_id, title=title, description=description)
#     if not topic:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to create topic")
#     return topic

# def read_topics(topic_id: int = None, user_id: int = None):
#     topics = topic_model.ReadTopic(topic_id=topic_id, user_id=user_id)
#     if not topics:
#         if topic_id:
#             raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Topic not found")
#         return [] 
#     return topics

# def update_topic(topic_id: int, title: str = None, description: str = None):
#     success = topic_model.UpdateTopic(topic_id=topic_id, title=title, description=description)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to update topic")
#     return {"message": "Topic updated successfully"}

# def delete_topic(topic_id: int):
#     success = topic_model.DeleteTopic(topic_id=topic_id)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to delete topic")
#     return {"message": "Topic deleted successfully"}

# # Convenience functions
# def get_all_topics():
#     return read_topics()

# def get_user_topics(user_id: int):
#     return read_topics(user_id=user_id)

# def get_topic(topic_id: int):
#     topics = read_topics(topic_id=topic_id)
#     return topics[0] if topics else None