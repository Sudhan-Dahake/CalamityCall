import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class UserModel:
    def __init__(self, tableName: str = "useraccounts"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(
            self.supabase_url, self.supabase_key)

        self.tableName = tableName

    # username: str
    # password: str
    # preference_id: int
    # age: int
    # address: str
    # zip_code: str
    # city: str

    def CreateUser(self, **CredParams: dict[str, any]):
        response = self.client.from_(
            self.tableName).insert(CredParams).execute()

        if (response.data):
            print(f"User registered successfully")

            return response.data[0]['userid']         # returns the userID

        else:
            print(f"Error registering user")

            return None

    def GetUser(self, userID: int):
        response = self.client.from_(self.tableName).select("username, password, preferenceid, age, address, zip_code, city").eq("userid", userID).execute()

        if (response.data):
            print(f"User Retrieved successfully")

            return response.data[0]

        else:
            print(f"Error retrieving user")

            return None

    # username: str
    # password: str
    # age: int
    # address: str
    # zip_code: str
    # city: str

    def UpdateUser(self, user_id: int, **UpdateParams: dict[str, any]):
        response = self.client.from_(self.tableName).update(
            UpdateParams).eq("userid", user_id).execute()
        
        # return response

        if (response.data):
            print(f"User {user_id} updated successfully.")
            return True
        else:
            print(f"Error updating user: User not found")
            return False

    def DeleteUser(self, user_id: int):
        pass


if __name__ == '__main__':
    user = UserModel()

    Created_useriD = user.CreateUser(username="Sudhan", password="verysEcUrE", preferenceid=1, age=21, address="Home", zip_code="N8G 4O3", city="Waterloo")

    print(user.GetUser(Created_useriD))

    user.UpdateUser(Created_useriD, password="eXtReMeLysEcUrE")

    print(user.GetUser(Created_useriD))
