package com.example.calamitycall.models.preference;

public class PreferenceUpdateResponse {
    private String Message;

    public PreferenceUpdateResponse(String Message) {
        this.Message = Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }
}
