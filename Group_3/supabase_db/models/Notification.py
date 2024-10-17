import os
from supabase import create_client, Client
from dotenv import load_dotenv
from datetime import datetime, timedelta, date

load_dotenv()


class NotificationModel:
    def __init__(self, tableName: str = "notifications"):
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
            "*").order("notifid", desc=True).limit(1).execute()

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
            "*").order("notifid", desc=True).limit(1)

        if latestNotifID:
            query = query.lt("notifid", latestNotifID)

        response = query.execute()

        if not response.data:
            print(f"No more Notifications to check")

            return None

        latestNotification = response.data[0]
        NotifID = latestNotification['notifid']
        NotifDate = latestNotification['notifdate']

        currentDate = datetime.now().date()
        NotifAge = (currentDate - datetime.strptime(NotifDate,
                    '%Y-%m-%d').date()).days

        NotifTimeFrameInDays = self.__ConvertTimeFrameToDays(timeFrame)

        if (NotifAge <= NotifTimeFrameInDays):
            self.NotifIDHistory.append(NotifID)

            return self.__recursiveNotifHistory(timeFrame, latestNotifID=NotifID)

        else:
            return None

    def GetNotifToDisplayForHistory(self, timeFrame: str = "1 month ago"):
        self.NotifIDHistory = []

        self.__recursiveNotifHistory(timeFrame=timeFrame)

        return self.__GetBunchOfNotifications()

    def __GetBunchOfNotifications(self):
        notifications_dict = {}

        response = self.client.from_(
            self.tableName).select("notiforigin, longitude, latitude, city, disastertype, disasterlevel, notifdate").in_("notifid", self.NotifIDHistory).execute()

        if (response.data):
            for notification, NotifID in zip(response.data, self.NotifIDHistory):
                notifications_dict[NotifID] = notification

        else:
            print(f"No notifications found")

        return notifications_dict

    def UpdateNotification(self):
        pass

    def DeleteNotification(self, timeFrame: str = "6 months ago", latestNotifID: int = None):
        query = self.client.from_(self.tableName).select(
            "notifid, notifdate").order("notifid").limit(1)

        if latestNotifID:
            query = query.gt("notifid", latestNotifID)

        response = query.execute()

        if response.data:
            latestNotification = response.data[0]
            NotifID = latestNotification['notifid']
            NotifDate = latestNotification['notifdate']

            currentDate = datetime.now().date()
            NotifAge = (currentDate - datetime.strptime(NotifDate,
                        '%Y-%m-%d').date()).days

            NotifTimeFrameInDays = self.__ConvertTimeFrameToDays(timeFrame)

            if (NotifAge > NotifTimeFrameInDays):
                response_del = self.client.from_(
                    self.tableName).delete().eq("notifid", NotifID).execute()

                return self.DeleteNotification(timeFrame=timeFrame, latestNotifID=NotifID)

            else:
                return None


if __name__ == '__main__':
    NotifModel = NotificationModel()

    results = NotifModel.CreateNotification(notiforigin='Test1', longitude=10.0, latitude=20.0, city='Waterloo', disastertype='Tornado', disasterlevel=3, notifdate=str(date(2024, 7, 12)))
    results = NotifModel.CreateNotification(notiforigin='Test2', longitude=10.0, latitude=20.0, city='Waterloo', disastertype='Hurricane', disasterlevel=3, notifdate=str(date(2024, 9, 12)))

    print(NotifModel.GetNotifToDisplayImmediately())

    NotifModel.DeleteNotification("1 week ago")

    results = NotifModel.CreateNotification(notiforigin='Test1', longitude=10.0, latitude=20.0, city='Waterloo', disastertype='Tornado', disasterlevel=3, notifdate=str(date(2024, 7, 12)))
    results = NotifModel.CreateNotification(notiforigin='Test2', longitude=10.0, latitude=20.0, city='Waterloo', disastertype='Hurricane', disasterlevel=3, notifdate=str(date(2024, 9, 12)))

    print(NotifModel.GetNotifToDisplayForHistory("6 months ago"))
