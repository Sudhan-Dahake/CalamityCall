package com.example.calamitycall.models.NotificationHistory;

import java.util.List;

public class ActiveNotificationResponse {
    private List<ActiveNotification> Notifications;

    // Getter and Setter
    public List<ActiveNotification> getNotifications() {
        return Notifications;
    }

    public void setNotifications(List<ActiveNotification> notifications) {
        Notifications = notifications;
    }
}
