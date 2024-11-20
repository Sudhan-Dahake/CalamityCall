package com.example.calamitycall.models.login;

public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private UserData user;

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
