import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class PostModel:
    def __init__(self, tableName: str = "posts"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreatePost(self, user_id: int, topic_id: int, content: str, image_url: str = None):
        post_data = {
            "user_id": user_id,
            "topic_id": topic_id,
            "content": content,
            "image_url": image_url
        }
        response = self.client.from_(self.tableName).insert(post_data).execute()

        if response.data:
            print("Post created successfully.")
            return response.data[0]
        else:
            print(f"Error creating post: {response.get('message', 'Unknown error')}")
            return None

    def ReadPost(self, post_id: int = None, user_id: int = None, topic_id: int = None):
        query = self.client.from_(self.tableName).select("*")

        if post_id:
            query = query.eq("post_id", post_id)
        if user_id:
            query = query.eq("user_id", user_id)
        if topic_id:
            query = query.eq("topic_id", topic_id)

        response = query.execute()
        if response.data:
            return response.data
        else:
            print(f"No posts found or error: {response.get('message', 'Unknown error')}")
            return None

    def UpdatePost(self, post_id: int, content: str = None, image_url: str = None):
        updated_fields = {}
        if content:
            updated_fields["content"] = content
        if image_url:
            updated_fields["image_url"] = image_url

        response = self.client.from_(self.tableName).update(updated_fields).eq("post_id", post_id).execute()

        if response.status_code == 204:
            print(f"Post {post_id} updated successfully.")
            return True
        else:
            print(f"Error updating post: {response.get('message', 'Unknown error')}")
            return False

    def DeletePost(self, post_id: int):
        response = self.client.from_(self.tableName).delete().eq("post_id", post_id).execute()

        if response.status_code == 204:
            print(f"Post {post_id} deleted successfully.")
            return True
        else:
            print(f"Error deleting post: {response.get('message', 'Unknown error')}")
            return False
