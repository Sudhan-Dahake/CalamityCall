from . import BasePreference

class NotificationAlertTypeModel(BasePreference):
    def __init__(self):
        super().__init__(tableName="notification_alert_type")