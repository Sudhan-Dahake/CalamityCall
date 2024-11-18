from . import BasePreference

class NotificationOnModel(BasePreference):
    def __init__(self):
        super().__init__(tableName="notification_on")