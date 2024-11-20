# from fastapi import HTTPException, status
# from ..models.posts import PostModel

# post_model = PostModel()

# def create_post(user_id: int, topic_id: int, content: str, image_url: str = None):
#     new_post = post_model.CreatePost(user_id=user_id, topic_id=topic_id, content=content, image_url=image_url)
#     if not new_post:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to create post")
#     return new_post

# def read_posts(post_id: int = None, user_id: int = None, topic_id: int = None):
#     posts = post_model.ReadPost(post_id=post_id, user_id=user_id, topic_id=topic_id)
#     if not posts:
#         if post_id:
#             raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Post not found")
#         return []  
#     return posts

# def update_post(post_id: int, content: str = None, image_url: str = None):
#     success = post_model.UpdatePost(post_id=post_id, content=content, image_url=image_url)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to update post")
#     return {"message": "Post updated successfully"}

# def delete_post(post_id: int):
#     success = post_model.DeletePost(post_id=post_id)
#     if not success:
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Failed to delete post")
#     return {"message": "Post deleted successfully"}

# # def ReportPost(post_id: int, reason: str):
# #     success = posts_model.ReportPost(post_id=post_id, reason=reason)
# #     if not success:
# #         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Unable to report post")
# #     return {"message": "Post reported successfully"} 

# # Convenience functions
# def get_posts_by_topic(topic_id: int):
#     return read_posts(topic_id=topic_id)

# def get_posts_by_user(user_id: int):
#     return read_posts(user_id=user_id)

# def get_post(post_id: int):
#     posts = read_posts(post_id=post_id)
#     return posts[0] if posts else None