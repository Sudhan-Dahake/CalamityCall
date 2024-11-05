package com.example.calamitycall.Notif_Management;

import com.google.gson.annotations.SerializedName;

// Class to define the Preferences Response structure
public class PreferencesResponse {
    // only gets a message back
    @SerializedName("Message")
    private String Message;

    // Getters only
    public String getMessage() {
        return this.Message;
    }
}
