package com.example.calamitycall.models.token;

public class VerifyTokenResponse {
    private String message;
    private String username;


    public VerifyTokenResponse(String message, String username) {
        this.message = message;
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
