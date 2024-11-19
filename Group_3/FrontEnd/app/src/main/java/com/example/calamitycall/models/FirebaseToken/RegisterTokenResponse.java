package com.example.calamitycall.models.FirebaseToken;

public class RegisterTokenResponse {
    private String status;

    public RegisterTokenResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
