import os
from supabase import create_client, Client
from dotenv import load_dotenv
from datetime import datetime, timedelta

load_dotenv()


class NotificationModel:
    def __init__(self, tableName: str = "Notification"):
        self.supabase_url = os.getenv("SUPABASE_URL")
        self.supabase_key = os.getenv("SUPABASE_KEY")
        self.client: Client = create_client(
            self.supabase_url, self.supabase_key)

        self.tableName = tableName
        self.NotifID: list = []
        self.NotifIDHistory: list = []

    def CreateNotification(self, **NotifParams: dict[str, any]) -> bool:
        response = self.client.from_(
            self.tableName).insert(NotifParams).execute()

        if (response.data):
            print(f"Notification stored successfully")
            self.NotifID.append(response.data[0])

            return True

        else:
            print(f"Error storing Notification")

            return False

    def GetNotifToDisplayImmediately(self):
        # Fetch the latest notification based on highest NotifID
        response = self.client.from_(self.tableName).select(
            "*").order("NotifID", desc=True).limit(1).execute()

        if (response.data):
            print(f"Latest Notification Retrieved Successfully")

            return response.data[0]

        else:
            print(f"Error Retrieving Latest Notification")

            return None

    # private function

    def __ConvertTimeFrameToDays(self, timeFrame: str) -> int:
        if (timeFrame == "1 week ago"):
            return 7

        elif (timeFrame == "1 month ago"):
            return 31

        elif (timeFrame == "3 months ago"):
            return 92

        elif (timeFrame == "6 months ago"):
            return 184

        else:
            return None

    # private function

    def __recursiveNotifHistory(self, timeFrame: str, latestNotifID: int = None):
        query = self.client.from_(self.tableName).select(
            "*").order("NotifID", desc=True).limit(1)

        if latestNotifID:
            query = query.lt("NotifID", latestNotifID)

        response = query.execute()

        if not response.data:
            print(f"No more Notifications to check")

            return None

        latestNotification = response.data[0]
        NotifID = latestNotification['NotifID']
        NotifDate = latestNotification['NotifDate']

        currentDate = datetime.now().date()
        NotifAge = (currentDate - datetime.strptime(NotifDate,
                    '%Y-%m-%d').date()).days

        NotifTimeFrameInDays = self.__ConvertTimeFrameToDays(timeFrame)

        if (NotifAge <= NotifTimeFrameInDays):
            self.NotifIDHistory.append(NotifID)

            return self.__recursiveNotifHistory(timeFrame, latestNotifID=NotifID)

        else:
            return None

    def GetNotifToDisplayForHistory(self, timeFrame: str, latestNotifID: int = None):
        self.NotifIDHistory = []

        self.__recursiveNotifHistory(
            timeFrame=timeFrame, latestNotifID=latestNotifID)

    def GetBunchOfNotifications(self):
        notifications_dict = {}

        response = self.client.from_(
            self.tableName).select("NotifOrigin, Longitude, Latitude, City, DisasterType, DisasterLevel, NotifDate").in_("NotifID", self.NotifIDHistory).execute()

        if (response.data):
            for notification, NotifID in zip(response.data, self.NotifIDHistory):
                notifications_dict[NotifID] = notification

        else:
            print(f"No notifications found")

        return notifications_dict

    def UpdateNotification(self):
        pass

    def DeleteNotification(self, timeFrame: str = "6 months ago", latestNotifID: int = None):
        query = self.client.from_(
            self.tableName).select("NotifID, NotifDate").order("NotifID").limit(1)

        if latestNotifID:
            query = query.gt("NotifID", latestNotifID)

        response = query.execute()

        latestNotification = response.data[0]
        NotifID = latestNotification['NotifID']
        NotifDate = latestNotification['NotifDate']

        currentDate = datetime.now().date()
        NotifAge = (currentDate - datetime.strptime(NotifDate,
                    '%Y-%m-%d').date()).days

        NotifTimeFrameInDays = self.__ConvertTimeFrameToDays(timeFrame)

        if (NotifAge > NotifTimeFrameInDays):
            response_del = self.client.from_(
                self.tableName).delete().eq("NotifID", NotifID).execute()

            return self.DeleteNotification(timeFrame=timeFrame, latestNotifID=NotifID)

        else:
            return None
