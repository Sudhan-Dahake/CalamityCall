package com.example.calamitycall.models.signup;

public class SignupResponse {
    private String message;

    public SignupResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
