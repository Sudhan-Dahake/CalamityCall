package com.example.calamitycall.models.token;

public class TokenResponse {
    private String access_token;
    private String token_type;

    public TokenResponse(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }


    //setters
    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }


    //getters
    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }
}
