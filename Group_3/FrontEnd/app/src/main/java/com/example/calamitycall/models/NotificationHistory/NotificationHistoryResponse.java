package com.example.calamitycall.models.NotificationHistory;
import java.util.List;

public class NotificationHistoryResponse {
    private List<NotificationResponse> Notifications;

    // Getter and Setter
    public List<NotificationResponse> getNotifications() {
        return Notifications;
    }

    public void setNotifications(List<NotificationResponse> notifications) {
        Notifications = notifications;
    }
}
