import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()


class PreferencesModel:
    def __init__(self, tableName: str = "preferences"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(
            self.supabase_url, self.supabase_key)

        self.tableName = tableName
        self.preference_id = None

    def CreatePreference(self, NotificationType: str = "Push", DisasterType: str = "All", SeverityType: int = 3, NotifFlashing: bool = True, TextToSpeech: bool = True, NotifTimeFrame: str = "6 months ago"):
        preference = {
            "notificationtype": NotificationType,
            "disastertype": DisasterType,
            "severitytype": SeverityType,
            "notifflashing": NotifFlashing,
            "texttospeech": TextToSpeech,
            "notiftimeframe": NotifTimeFrame
        }

        response = self.client.from_(
            self.tableName).insert(preference).execute()

        if response.data:
            print(f"Preferences created Successfully")
            self.preference_id = response.data[0]['preferenceid']

            return True

        else:
            print(f"Error Creating Preferences")

            return False

    def GetPreference(self):
        if (self.preference_id):
            response = self.client.from_(self.tableName).select(
                "*").eq("preferenceid", self.preference_id).execute()

            if (response.data):
                print(f"Preferences Retrieved Successfully")

                # Returns the preference data as a dictionary
                return response.data[0]

            else:
                print(f"PreferenceID: {self.preference_id} not found.")

                return None

        else:
            print(
                f"PreferenceID is set to None. Please create a Default Preference for the current user first.")

            return None

    def UpdatePreference(self, **updatedPreferences: dict[str, any]) -> bool:
        if (self.preference_id):
            response = self.client.from_(self.tableName).update(
                updatedPreferences).eq("preferenceid", self.preference_id).execute()

            if (response.data):
                print(
                    f"Preference with ID: {self.preference_id} updated successfully")

                return True

            else:
                print(
                    f"Error updating preference with ID: {self.preference_id}")

                return False

        else:
            print(
                f"PreferenceID is set to None. Please create a Default Preference for the current user first.")

            return False

    def DeletePreference(self):
        pass
