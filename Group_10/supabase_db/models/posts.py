import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class PostsModel:
    def __init__(self, tableName: str = "posts"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreatePost(self, content: str, user_id: int, image_url: str = None):
        post_data = {
            "content": content,
            "user_id": user_id,
            "image_url": image_url
        }
        response = self.client.from_(self.tableName).insert(post_data).execute()

        if response.status_code == 201:
            print("Post created successfully.")
            return response.data
        else:
            print(f"Error creating post: {response.get('message', 'Unknown error')}")
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
