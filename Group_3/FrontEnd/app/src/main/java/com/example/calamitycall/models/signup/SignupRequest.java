package com.example.calamitycall.models.signup;

public class SignupRequest {
    private String username;
    private String password;
    private int age;
    private String address;
    private String zip_code;
    private String city;


    public SignupRequest(String username, String password, int age, String address, String zip_code, String city) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.address = address;
        this.zip_code = zip_code;
        this.city = city;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
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
