import os
from supabase import create_client, Client
from dotenv import load_dotenv
from datetime import datetime, timedelta, date
from pydantic import BaseModel

load_dotenv()

class BasePreference:
    def __init__(self, tableName: str):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(self.supabase_url, self.supabase_key)
        self.tableName = tableName

    def Insert(self, data: BaseModel):
        response = self.client.from_(self.tableName).insert(data.model_dump()).execute()

        return response.data

    def Update(self, data: BaseModel):
        response = self.client.from_(self.tableName).update(data.model_dump()).eq("preferenceid", data.preferenceid).execute()

        return response.data is not None

    def Delete(self, preferenceid: int):
        pass

    def GetByPreferenceID(self, preferenceid: int):
        response = self.client.from_(self.tableName).select("*").eq("preferenceid", preferenceid).execute()

        return response.data[0] if response.data else None