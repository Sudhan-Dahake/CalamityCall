import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class UserModel:
    def __init__(self, tableName: str = "Credentials"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(
            self.supabase_url, self.supabase_key)

        self.id = None
        self.username = None
        self.password = None
        self.tableName = tableName

    def CreateUser(self, username: str, password: str, preference_id: int = None):
        pass

    def UpdateUser(self, user_id: int, username: str, password: str):
        updated_fields = {}

        if username:
            updated_fields["username"] = username

        if password:
            updated_fields["password"] = password

        response = self.client.from_(self.tableName).update(
            updated_fields).eq("UserID", user_id).execute()

        if (response.status_code == 204):
            print(f"User {user_id} updated successfully.")
            return True
        else:
            print(
                f"Error updating user: {response.get('message', 'Unknown error')}")
            return False

    def DeleteUser(self, user_id: int):
        response = self.client.from_(self.tableName).delete().eq(
            "UserID", user_id).execute()

        if response.status_code == 204:
            print(f"User {user_id} deleted successfully.")
            return True
        else:
            print(
                f"Error deleting user: {response.get('message', 'Unknown error')}")
            return False
