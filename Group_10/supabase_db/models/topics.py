import os
from supabase import create_client, Client
from dotenv import load_dotenv
from datetime import datetime, timezone 
load_dotenv()


class TopicModel:
    def __init__(self, tableName: str = "topics"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreateTopic(self, user_id: int, title: str, description: str = "", created_at: datetime = None):
        
        if not created_at:
            created_at = datetime.now(timezone.utc).replace(microsecond=0)
        
        topic_data = {
            "user_id": user_id,
            "title": title,
            "description": description,
            "created_at": created_at.isoformat()  # Use isoformat to ensure it's in correct string format
        }
        response = self.client.from_(self.tableName).insert(topic_data).execute()

        if response.data:
            print("Topic created successfully.")
            return response.data[0]
        else:
            print(f"Error creating topic: {response.get('message', 'Unknown error')}")
            return None

    def ReadTopic(self, topic_id: int = None, user_id: int = None):
        query = self.client.from_(self.tableName).select("*")

        if topic_id:
            query = query.eq("topic_id", topic_id)
        if user_id:
            query = query.eq("user_id", user_id)

        response = query.execute()
        if response.data:
            return response.data
        else:
            print(f"No topics found or error: {response.get('message', 'Unknown error')}")
            return None

    def UpdateTopic(self, topic_id: int, title: str = None, description: str = None):
        updated_fields = {}
        if title:
            updated_fields["title"] = title
        if description:
            updated_fields["description"] = description

        response = self.client.from_(self.tableName).update(updated_fields).eq("topic_id", topic_id).execute()

        if response.status_code == 204:
            print(f"Topic {topic_id} updated successfully.")
            return True
        else:
            print(f"Error updating topic: {response.get('message', 'Unknown error')}")
            return False

    def DeleteTopic(self, topic_id: int):
        response = self.client.from_(self.tableName).delete().eq("topic_id", topic_id).execute()

        if response.status_code == 204:
            print(f"Topic {topic_id} deleted successfully.")
            return True
        else:
            print(f"Error deleting topic: {response.get('message', 'Unknown error')}")
            return False