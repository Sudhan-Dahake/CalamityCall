package com.example.calamitycall.Notif_Management;

import com.google.gson.annotations.SerializedName;

// Class that defines Preferences Request structure
public class PreferencesRequest {

    // Object with all preference attributes
    @SerializedName("notificationtype")
    private int notificationtype;
    @SerializedName("disastertype")
    private String disastertype;
    @SerializedName("severitytype")
    private int severitytype;
    @SerializedName("notifflashing")
    private Boolean notifflashing;
    @SerializedName("texttospeech")
    private Boolean texttospeech;
    @SerializedName("notiftimeframe")
    private String notiftimeframe;

    public PreferencesRequest(){
        notificationtype = 0;
        disastertype = null;
        severitytype = 0;
        notifflashing = null;
        texttospeech = null;
        notiftimeframe = null;
    }

    public PreferencesRequest(int NotificationType, String DisasterType, int SeverityType, Boolean NotifFlashing,
                              Boolean TextToSpeech, String NotifTimeFrame){
        notificationtype = NotificationType;
        disastertype = DisasterType;
        severitytype = SeverityType;
        notifflashing = NotifFlashing;
        texttospeech = TextToSpeech;
        notiftimeframe = NotifTimeFrame;
    }

    // GETTERS
    public int getNotificationType(){
        return notificationtype;
    }
    public String getDisasterType(){
        return disastertype;
    }
    public int getSeverityType(){
        return severitytype;
    }
    public Boolean getNotifFlashing(){
        return notifflashing;
    }
    public Boolean getTextToSpeech(){
        return texttospeech;
    }
    public String getNotifTimeFrame(){
        return notiftimeframe;
    }

    // SETTERS
    public void setNotificationType(int NotificationType){
        notificationtype = NotificationType;
    }
    public void setDisasterType(String DisasterType){
        disastertype = DisasterType;
    }
    public void setSeverityType(int SeverityType){
        severitytype = SeverityType;
    }
    public void setNotifFlashing(Boolean NotifFlashing){
        notifflashing = NotifFlashing;
    }
    public void setTextToSpeech(Boolean TextToSpeech){
        texttospeech = TextToSpeech;
    }
    public void setNotifTimeFrame(String NotifTimeFrame){
        notiftimeframe = NotifTimeFrame;
    }
}
