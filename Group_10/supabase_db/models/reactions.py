import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class ReactionsModel:
    def __init__(self, tableName: str = "reactions"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

class ReactionsModel:
    def __init__(self, tableName: str = "reactions"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreateReaction(self, user_id: int, post_id: int, emoji_type: int):
        reaction_data = {
            "user_id": user_id,
            "post_id": post_id,
            "emoji_type": emoji_type
        }
        response = self.client.from_(self.tableName).insert(reaction_data).execute()

        if response.data:
            print("Reaction created successfully.")
            return response.data[0]
        elif response.status_code == 409:  # Unique constraint violation
            print("User has already reacted to this post.")
            return None
        else:
            print(f"Error creating reaction: {response.get('message', 'Unknown error')}")
            return None

    def ReadReaction(self, reaction_id: int = None, user_id: int = None, post_id: int = None):
        query = self.client.from_(self.tableName).select("*")

        if reaction_id:
            query = query.eq("reaction_id", reaction_id)
        if user_id:
            query = query.eq("user_id", user_id)
        if post_id:
            query = query.eq("post_id", post_id)

        response = query.execute()
        if response.data:
            return response.data
        else:
            print(f"No reactions found or error: {response.get('message', 'Unknown error')}")
            return None

    def UpdateReaction(self, reaction_id: int, emoji_type: int):
        updated_fields = {"emoji_type": emoji_type}

        response = self.client.from_(self.tableName).update(updated_fields).eq("reaction_id", reaction_id).execute()

        if response.status_code == 204:
            print(f"Reaction {reaction_id} updated successfully.")
            return True
        else:
            print(f"Error updating reaction: {response.get('message', 'Unknown error')}")
            return False

    def DeleteReaction(self, reaction_id: int):
        response = self.client.from_(self.tableName).delete().eq("reaction_id", reaction_id).execute()

        if response.status_code == 204:
            print(f"Reaction {reaction_id} deleted successfully.")
            return True
        else:
            print(f"Error deleting reaction: {response.get('message', 'Unknown error')}")
            return False