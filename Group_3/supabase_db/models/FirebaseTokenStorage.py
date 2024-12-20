import os
from supabase import create_client, Client
from dotenv import load_dotenv
from datetime import datetime
from ..schemas.FirebaseTokenStorage import FirebaseTokenCreate, FirebaseTokenUpdate

load_dotenv()

class FirebaseTokenStorageModel:
    def __init__(self, tableName: str = "firebasetokenstorage"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def CreateToken(self, firebaseTokenInformation: FirebaseTokenCreate) -> bool:
        data = firebaseTokenInformation.model_dump(exclude_none=True)

        print(firebaseTokenInformation.model_dump())

        response = self.client.from_(self.tableName).upsert(data, on_conflict=["userid, deviceid"]).execute()

        if response.data:
            print(f"Token Stored Successfully")

            return True

        else:
            print(f"Error Storing Token")

            return False

    def getAllUnregisteredTokens(self):
        response = self.client.from_(self.tableName).select("fcmtoken").is_("userid", None).execute()

        if response.data:
            print("Unregistered tokens retrieved successfully")

            return [record["fcmtoken"] for record in response.data]

        else:
            print("Error retrieving unregistered tokens")

            return None


    def getAllRegisteredTokens(self):
        # raw_query = f"SELECT fcmtoken, notificationtype FROM {self.tableName} WHERE userid IS NOT NULL;"
        # response = self.client.execute_sql(raw_query)
        # response = self.client.from_(self.tableName).select("fcmtoken, notificationtype").neq("userid", None).execute()

        response = self.client.from_(self.tableName).select("fcmtoken, notificationtype, userid").execute()

        if not response or not response.data:
            print("Error retrieving data or table is empty")
            return False

        all_rows = response.data

        # Step 2: Separate rows where userid is NULL
        null_rows = [row for row in all_rows if row["userid"] is None]

        # Step 3: Subtract rows with NULL userid to get rows with NOT NULL userid
        registered_rows = [row for row in all_rows if row not in null_rows]

        if registered_rows:
            print(f"Registered token retrieved successfully")

            return [{"fcmtoken": record["fcmtoken"], "notificationtype": record["notificationtype"]} for record in response.data]

        else:
            print(f"Error retrieving registered tokens")

            return False


    def UpdateTokenData(self, userid: int, deviceid: str, tokenInformation: FirebaseTokenUpdate) -> bool:
        data = tokenInformation.model_dump(exclude_none=True)

        response = self.client.from_(self.tableName).update(data).eq("userid", userid).eq("deviceid", deviceid).execute()

        if response.data:
            print(f"Token Information updated successfully")

            return True

        else:
            print(f"Error updating token information")

            return False


    def DeleteTokenData(self, userid: int, deviceid: int) -> bool:
        response = self.client.from_(self.tableName).delete().eq("userid", userid).eq("deviceid", deviceid).execute()

        if response.data:
            print(f"Token deleted successfully")

            return True

        else:
            print(f"Error deleting token")

            return False