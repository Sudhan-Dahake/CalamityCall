package com.example.calamitycall.models.NotificationHistory;

public class ActiveNotification {
    private String notiforigin;
    private double longitude;
    private double latitude;
    private String city;
    private String disastertype;
    private int disasterlevel;
    private String notifdate;
    private String notiftime;
    private String preparationsteps; // Optional field
    private String activesteps;      // Optional field
    private String recoverysteps;    // Optional field


    public ActiveNotification(String notiforigin, double longitude, double latitude, String city, String disastertype, int disasterlevel, String notifdate, String notiftime, String preparationsteps, String activesteps, String recoverysteps) {
        this.notiforigin = notiforigin;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.disastertype = disastertype;
        this.disasterlevel = disasterlevel;
        this.notifdate = notifdate;
        this.notiftime = notiftime;
        this.preparationsteps = preparationsteps;
        this.activesteps = activesteps;
        this.recoverysteps = recoverysteps;
    }

    public ActiveNotification(String notiforigin, double longitude, double latitude, String city, String disastertype, int disasterlevel, String notifdate) {
        this.notiforigin = notiforigin;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.disastertype = disastertype;
        this.disasterlevel = disasterlevel;
        this.notifdate = notifdate;
    }


    // Getters and Setters
    public String getNotiforigin() {
        return notiforigin;
    }

    public void setNotiforigin(String notiforigin) {
        this.notiforigin = notiforigin;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisastertype() {
        return disastertype;
    }

    public void setDisastertype(String disastertype) {
        this.disastertype = disastertype;
    }

    public int getDisasterlevel() {
        return disasterlevel;
    }

    public void setDisasterlevel(int disasterlevel) {
        this.disasterlevel = disasterlevel;
    }

    public String getNotifdate() {
        return notifdate;
    }

    public void setNotifdate(String notifdate) {
        this.notifdate = notifdate;
    }

    public String getNotiftime() {
        return notiftime;
    }

    public void setNotiftime(String notiftime) {
        this.notiftime = notiftime;
    }

    public String getPreparationsteps() {
        return preparationsteps;
    }

    public void setPreparationsteps(String preparationsteps) {
        this.preparationsteps = preparationsteps;
    }

    public String getActivesteps() {
        return activesteps;
    }

    public void setActivesteps(String activesteps) {
        this.activesteps = activesteps;
    }

    public String getRecoverysteps() {
        return recoverysteps;
    }

    public void setRecoverysteps(String recoverysteps) {
        this.recoverysteps = recoverysteps;
    }
}
