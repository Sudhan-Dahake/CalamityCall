package com.example.calamitycall.models.FirebaseToken;

public class RegisterTokenRequest {
    private String fcmtoken;
    private Integer userid;
    private String deviceid;


    public RegisterTokenRequest(String fcmtoken, String deviceid, Integer userid) {
        this.fcmtoken = fcmtoken;
        this.userid = userid;
        this.deviceid = deviceid;
    }


    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
