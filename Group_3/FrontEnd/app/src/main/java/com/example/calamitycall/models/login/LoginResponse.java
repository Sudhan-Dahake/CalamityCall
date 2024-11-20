package com.example.calamitycall.models.login;

public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private UserData user;

    public LoginResponse(String access_token, String refresh_token, String token_type, UserData user) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.token_type = token_type;
        this.user = user;
    }

    public String getAccessToken() {
        return this.access_token;
    }

    public String getRefreshToken() {
        return this.refresh_token;
    }

    public String getTokenType() {
        return this.token_type;
    }

    public UserData getUser() {
        return this.user;
    }



    public static class UserData {
        private String username;
        private int preferenceID;
        private int age;
        private String address;
        private String zip_code;
        private String city;

        public UserData(String username, int preferenceID, int age, String address, String zip_code, String city) {
            this.username = username;
            this.preferenceID = preferenceID;
            this.age = age;
            this.address = address;
            this.zip_code = zip_code;
            this.city = city;
        }

        public String getUsername() {
            return this.username;
        }

        public int getPreferenceID() {
            return this.preferenceID;
        }

        public int getAge() {
            return this.age;
        }

        public String getAddress() {
            return this.address;
        }

        public String getZipCode() {
            return this.zip_code;
        }

        public String getCity() {
            return this.city;
        }
    }
}
