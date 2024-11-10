# from __future__ import annotations
import os
from supabase import create_client, Client
from dotenv import load_dotenv

from . import BasePreference
from ..schemas import UpdatedPreferenceValues

load_dotenv()

class PreferencesModel:
    def __init__(self):
        from . import NotificationOnModel, NoiseModel, FlashingModel, NotificationAlertTypeModel, TextToSpeechModel

        self.main_preferences = BasePreference(tableName="preferences")
        self.notification_on = NotificationOnModel()
        self.noise = NoiseModel()
        self.flashing = FlashingModel()
        self.notification_alert_type = NotificationAlertTypeModel()
        self.text_to_speech = TextToSpeechModel()

    def __NotificationOnDefaultValue(self):
        from ..schemas import NotificationOnValues

        notificationOnValues = NotificationOnValues()

        notificationOnValues.watch = True
        notificationOnValues.warning = True
        notificationOnValues.urgent = True
        notificationOnValues.critical = True

        return notificationOnValues

    def __NoiseDefaultValues(self):
        from ..schemas import NoiseValues

        noiseValues = NoiseValues()

        noiseValues.watch = True
        noiseValues.warning = True
        noiseValues.urgent = True
        noiseValues.critical = True

        return noiseValues

    def __FlashingDefaultValues(self):
        from ..schemas import FlashingValues

        flashingValues = FlashingValues()

        flashingValues.watch = False
        flashingValues.warning = False
        flashingValues.urgent = False
        flashingValues.critical = False

        return flashingValues

    def __NotificationAlertTypeDefaultValues(self):
        from ..schemas import NotificationAlertTypeValues

        notificationAlertTypeValues = NotificationAlertTypeValues()

        notificationAlertTypeValues.watch = "Push"
        notificationAlertTypeValues.warning = "Push"
        notificationAlertTypeValues.urgent = "Push"
        notificationAlertTypeValues.critical = "Push"

        return notificationAlertTypeValues

    def __TextToSpeechDefaultValues(self):
        from ..schemas import TextToSpeechValues

        textToSpeechValues = TextToSpeechValues()

        textToSpeechValues.watch = False
        textToSpeechValues.warning = False
        textToSpeechValues.urgent = False
        textToSpeechValues.critical = False

        return textToSpeechValues


    def CreatePreferenceSet(self):
        from ..schemas import MainPreferenceValues

        preference = self.main_preferences.Insert(MainPreferenceValues())

        if preference:
            preferenceID = preference[0]["preferenceid"]


            notificationOnValues = self.__NotificationOnDefaultValue()
            notificationOnValues.preferenceid = preferenceID

            noiseValues = self.__NoiseDefaultValues()
            noiseValues.preferenceid = preferenceID

            flashingValues = self.__FlashingDefaultValues()
            flashingValues.preferenceid = preferenceID

            notificationAlertTypeValues = self.__NotificationAlertTypeDefaultValues()
            notificationAlertTypeValues.preferenceid = preferenceID

            textToSpeechValues = self.__TextToSpeechDefaultValues()
            textToSpeechValues.preferenceid = preferenceID


            #Insert into each table
            self.notification_on.Insert(notificationOnValues)
            self.noise.Insert(noiseValues)
            self.flashing.Insert(flashingValues)
            self.notification_alert_type.Insert(notificationAlertTypeValues)
            self.text_to_speech.Insert(textToSpeechValues)


            return preferenceID

        else:
            print("Error Creating Preference set")

            return None


    def GetFullPreferenceSet(self, preferenceid: int):
        return {
            "notification_on": self.notification_on.GetByPreferenceID(preferenceid=preferenceid),
            "noise": self.noise.GetByPreferenceID(preferenceid=preferenceid),
            "flashing": self.flashing.GetByPreferenceID(preferenceid=preferenceid),
            "notification_alert_type": self.notification_alert_type.GetByPreferenceID(preferenceid=preferenceid),
            "text_to_speech": self.text_to_speech.GetByPreferenceID(preferenceid=preferenceid)
        }

    def UpdatePreference(self, updatedPreferenceValues: UpdatedPreferenceValues):
        # from . import UpdatedPreferenceValues

        table_map = {
            "notification_on": self.notification_on,
            "noise": self.noise,
            "flashing": self.flashing,
            "notification_alert_type": self.notification_alert_type,
            "text_to_speech": self.text_to_speech
        }

        if updatedPreferenceValues.tableName in table_map:
            return table_map[updatedPreferenceValues.tableName].update(updatedPreferenceValues.preferenceid, updatedPreferenceValues.data.model_dump())

        else:
            print(f"Table {updatedPreferenceValues.tableName} not found")

            return None


    def DeletePreference(self):
        pass




# class PreferencesModel:
#     def __init__(self, tableName: str = "preferences"):
#         self.supabase_url = os.getenv("SUPABASE_URL")
#         self.supabase_key = os.getenv("SUPABASE_KEY")
#         self.client: Client = create_client(
#             self.supabase_url, self.supabase_key)

#         self.tableName = tableName

#     def CreatePreference(self, NotificationType: str = "Push", DisasterType: str = "All", SeverityType: int = 3, NotifFlashing: bool = True, TextToSpeech: bool = True, NotifTimeFrame: str = "6 months ago"):
#         preference = {
#             "notificationtype": NotificationType,
#             "disastertype": DisasterType,
#             "severitytype": SeverityType,
#             "notifflashing": NotifFlashing,
#             "texttospeech": TextToSpeech,
#             "notiftimeframe": NotifTimeFrame
#         }

#         response = self.client.from_(
#             self.tableName).insert(preference).execute()

#         if response.data:
#             print(f"Preferences created Successfully")

#             return response.data[0]['preferenceid']

#         else:
#             print(f"Error Creating Preferences")

#             return None

#     def GetPreference(self, preference_id: int):
#         if (preference_id):
#             response = self.client.from_(self.tableName).select(
#                 "notificationtype, disastertype, severitytype, notifflashing, texttospeech, notiftimeframe").eq("preferenceid", preference_id).execute()

#             if (response.data):
#                 print(f"Preferences Retrieved Successfully")

#                 # Returns the preference data as a dictionary
#                 return response.data[0]

#             else:
#                 print(f"PreferenceID: {preference_id} not found.")

#                 return None

#         else:
#             print(
#                 f"PreferenceID is set to None. Please create a Default Preference for the current user first.")

#             return None

#     def UpdatePreference(self, preference_id: int, **updatedPreferences: dict[str, any]) -> bool:
#         response = self.client.from_(self.tableName).update(
#             updatedPreferences).eq("preferenceid", preference_id).execute()

#         if (response.data):
#             print(f"Preference with ID: {preference_id} updated successfully")

#             return True

#         else:
#             print(f"Error updating preference with ID: {preference_id}")

#             return False

#     def DeletePreference(self):
#         pass


# if __name__ == '__main__':
#     Preference = PreferencesModel()

#     Preference_id = Preference.CreatePreference()

#     print(Preference_id)

#     print()
#     print()

#     print(Preference.GetPreference(Preference_id))

#     print()
#     print()

#     updatePreferenceResult = Preference.UpdatePreference(
#         Preference_id, notificationtype="Pop", disastertype="Tornado", severitytype=2, notifflashing=True, texttospeech=True, notiftimeframe="6 months ago")

#     print(updatePreferenceResult)
