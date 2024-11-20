package com.example.calamitycall.firebase;

public class FCMData {
    private String notifOrigin;
    private Float longitude;
    private Float latitude;
    private String city;
    private String disasterType;
    private int disasterLevel;
    private String notifDate;
    private String preparationSteps;
    private String activeSteps;
    private String recoverySteps;
    private boolean force_popup;


    public FCMData(String notifOrigin, Float longitude, Float latitude, String city, String disasterType, int disasterLevel, String notifDate, String preparationSteps, String activeSteps, String recoverySteps, boolean force_popup) {
        this.notifOrigin = notifOrigin;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.disasterType = disasterType;
        this.disasterLevel = disasterLevel;
        this.notifDate = notifDate;
        this.preparationSteps = preparationSteps;
        this.activeSteps = activeSteps;
        this.recoverySteps = recoverySteps;
        this.force_popup = force_popup;
    }


    public FCMData(String notifOrigin, Float longitude, Float latitude, String city, String disasterType, int disasterLevel, String notifDate, boolean force_popup) {
        this(notifOrigin, longitude, latitude, city, disasterType, disasterLevel, notifDate, null, null, null, force_popup);
    }


    // setters
    public void setNotifOrigin(String notifOrigin) {
        this.notifOrigin = notifOrigin;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public void setDisasterLevel(int disasterLevel) {
        this.disasterLevel = disasterLevel;
    }

    public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public void setActiveSteps(String activeSteps) {
        this.activeSteps = activeSteps;
    }

    public void setRecoverySteps(String recoverySteps) {
        this.recoverySteps = recoverySteps;
    }

    public void setForce_popup(boolean force_popup) {
        this.force_popup = force_popup;
    }




    // getters

    public String getNotifOrigin() {
        return this.notifOrigin;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public String getCity() {
        return this.city;
    }

    public String getDisasterType() {
        return this.disasterType;
    }

    public int getDisasterLevel() {
        return this.disasterLevel;
    }

    public String getNotifDate() {
        return this.notifDate;
    }

    public String getPreparationSteps() {
        return this.preparationSteps;
    }

    public String getActiveSteps() {
        return this.activeSteps;
    }

    public String getRecoverySteps() {
        return this.recoverySteps;
    }

    public boolean getForcePopUp() {
        return this.force_popup;
    }
}
