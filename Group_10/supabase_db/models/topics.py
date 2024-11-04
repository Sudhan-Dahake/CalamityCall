import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class TopicsModel:
    def __init__(self, tableName: str = "topics"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreateTopic(self, title: str, description: str):
        topic_data = {
            "title": title,
            "description": description
        }
        response = self.client.from_(self.tableName).insert(topic_data).execute()

        if response.status_code == 201:
            print("Topic created successfully.")
            return response.data
        else:
            print(f"Error creating topic: {response.get('message', 'Unknown error')}")
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
