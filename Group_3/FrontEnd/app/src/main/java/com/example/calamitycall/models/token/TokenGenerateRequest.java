package com.example.calamitycall.models.token;

public class TokenGenerateRequest {
    private String refresh_token;

    public TokenGenerateRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefreshToken() {
        return this.refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
