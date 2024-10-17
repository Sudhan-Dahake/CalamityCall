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

    def CreateReaction(self, emoji_type: int, user_id: int, post_id: int):
        reaction_data = {
            "emoji_type": emoji_type,
            "user_id": user_id,
            "post_id": post_id
        }
        response = self.client.from_(self.tableName).insert(reaction_data).execute()

        if response.status_code == 201:
            print("Reaction created successfully.")
            return response.data
        else:
            print(f"Error creating reaction: {response.get('message', 'Unknown error')}")
            return None

    def UpdateReaction(self, reaction_id: int, emoji_type: int = None):
        updated_fields = {}

        if emoji_type is not None:
            updated_fields["emoji_type"] = emoji_type

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
