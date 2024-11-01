package com.example.calamitycall.Notif_Management;

import com.google.gson.annotations.SerializedName;

// Class to define Notification Response structure
public class NotificationResponse {
    // only receives a message back
    @SerializedName("Message")
    private String Message;

    // Getters only
    public String getMessage() {
        return this.Message;
    }
}