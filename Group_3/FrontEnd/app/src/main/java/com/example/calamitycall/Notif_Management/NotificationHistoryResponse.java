package com.example.calamitycall.Notif_Management;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// Class that defines the Notification History Response structure
public class NotificationHistoryResponse {
    // Receives a list of Notifications (Notification object structure is the same as the request packet, so its just re-used)
    @SerializedName("Notifications")
    private List<NotificationRequest> Notifications;

    public List<NotificationRequest> getNotificationHistory(){
        return Notifications;
    }
}
