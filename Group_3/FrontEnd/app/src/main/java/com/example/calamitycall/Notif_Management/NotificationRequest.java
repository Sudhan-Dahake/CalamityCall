package com.example.calamitycall.Notif_Management;

import com.google.gson.annotations.SerializedName;

// Class that defines the Notification information
public class NotificationRequest {

    // Notification properties
    @SerializedName("notiforigin")
    private String notiforigin;
    @SerializedName("disastertype")
    private String disastertype;
    @SerializedName("disasterlevel")
    private int disasterlevel;
    @SerializedName("notifdate")
    private String notifdate;
    @SerializedName("city")
    private String city;
    @SerializedName("longitude")
    private Float longitude;
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("preparationsteps")
    private String preparationsteps;
    @SerializedName("activesteps")
    private String activesteps;
    @SerializedName("recoverysteps")
    private String recoverysteps;


    // Default Constructor
    public NotificationRequest(){
        notiforigin = null;
        disastertype = null;
        disasterlevel = 0;
        notifdate = null;
        city = null;
        longitude = null;
        latitude = null;
        preparationsteps = null;
        activesteps = null;
        recoverysteps = null;
    }

    // Parameterized Constructor
    public NotificationRequest(String Origin, String DisasterType, int DisasterLevel,
                               String Date, String Location, Float Longitude, Float Latitude,
                               String PreparationSteps, String ActiveSteps, String RecoverySteps){
        notiforigin = Origin;
        disastertype = DisasterType;
        disasterlevel = DisasterLevel;
        notifdate = Date;
        city = Location;
        longitude = Longitude;
        latitude = Latitude;
        preparationsteps = PreparationSteps;
        activesteps = ActiveSteps;
        recoverysteps = RecoverySteps;
    }

    // GETTERS

    public String getNotifOrigin(){
        return notiforigin;
    }

    public String getDisasterType(){
        return disastertype;
    }

    public int getDisasterLevel(){
        return disasterlevel;
    }

    public String getNotifDate(){
        return notifdate;
    }

    public String getCity(){
        return city;
    }

    public Float getLongitude(){
        return longitude;
    }

    public Float getLatitude(){
        return latitude;
    }
    public String getPreparationSteps(){
        return preparationsteps;
    }
    public String getActiveSteps(){
        return activesteps;
    }
    public String getRecoverySteps(){
        return recoverysteps;
    }

    // SETTERS

    public void setNotifOrigin(String Origin){
        notiforigin = Origin;
    }

    public void setDisasterType(String DisasterType){
        disastertype = DisasterType;
    }

    public void setDisasterLevel(int DisasterLevel){
        disasterlevel = DisasterLevel;
    }

    public void setNotifDate(String Date){
        notifdate = Date;
    }

    public void setCity(String City){
        city = City;
    }

    public void setLongitude(Float Longitude){
        longitude = Longitude;
    }

    public void setLatitude(Float Latitude){
        latitude = Latitude;
    }

    public void setPreparationSteps(String PreparationSteps){
        preparationsteps = PreparationSteps;
    }
    public void setActiveSteps(String ActiveSteps){
        activesteps = ActiveSteps;
    }
    public void setRecoverySteps(String RecoverySteps){
        recoverysteps = RecoverySteps;
    }

}
