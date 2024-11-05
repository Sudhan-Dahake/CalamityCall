# posts_services.py
from fastapi import HTTPException, status
from ..models.posts import PostsModel
from ..models.topics import TopicsModel
from datetime import datetime

# Initialize PostsModel and TopicsModel
posts_model = PostsModel()
topic_model = TopicsModel()

# Get all discussion topics
def GetDiscussionTopics():
    topics = topic_model.GetTopics()
    if not topics:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No topics available")
    return topics

# Get posts from a specific topic
def GetPostsFromTopic(topic_id: int):
    posts = posts_model.GetPosts(topic_id=topic_id)
    if not posts:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="No posts available for this topic")
    return posts

# Add a new post to a topic
def AddPostToTopic(content: str, user_id: int, image_url: str = None):
    new_post = posts_model.CreatePost(content=content, user_id=user_id, image_url=image_url)
    if not new_post:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to create post")
    return new_post

# # Report a post for inappropriate content
# def ReportPost(post_id: int, reason: str):
#     success = posts_model.ReportPost(post_id=post_id, reason=reason)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Unable to report post")
#     return {"message": "Post reported successfully"} 