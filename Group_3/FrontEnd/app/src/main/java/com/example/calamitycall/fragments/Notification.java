package com.example.calamitycall.fragments;

import java.util.Date;

public class Notification {

    private String disaster_type;  // like tornado, etc.
    private String origin;
    private String city;
    private Integer level;  // "critical", "urgent", "warning", "watch"
    private String date;
    private Float longitude;
    private Float latitude;



    public Notification()
    {
        this.disaster_type = "";
        this.origin = "";
        this.city = "";
        this.level = 0;
        this.longitude = 0.F;
        this.latitude = 0.F;
        this.date = "0000-00-00";

    }

    public Notification(String disaster_type, Integer level, String date)
    {
        this.disaster_type = disaster_type;
        this.level = level;
        this.date = date;
    }


    public Notification(String disaster_type, Integer level)
    {
        this.disaster_type = disaster_type;
        this.level = level;
    }

    // Constructor
    public Notification(String disaster_type, String origin, String city,  Integer level,  String date, Float longitude, Float latitude) {
        this.disaster_type = disaster_type;
        this.origin = origin;
        this.city = city;
        this.level = level;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }




    public void ActiveNoitificationSetter(String disaster_type, Integer level)
    {
        this.disaster_type = disaster_type;
        this.level = level;
    }


    public void HistoryNotificationSetter(String disaster_type, Integer level, String date)
    {
        this.disaster_type = disaster_type;
        this.level = level;
        this.date = date;
    }



    // Defining the setters and  getters
    public void setDisasterType(String disaster_type)
    {
        this.disaster_type = disaster_type;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setCity(String city)
    {
        this.city = city;
    }


    public void setLevel(Integer level)
    {
        this.level = level;
    }


    public void setDate(String date)
    {
        this.date = date;
    }


    public void setLongitude(Float longitude)
    {
        this.longitude = longitude;
    }
    public void setLatitude(Float latitude)
    {
        this.latitude = latitude;
    }


    // Getters
    public String getDisasterType()
    {
        return this.disaster_type;
    }

    public String getOrigin()
    {
        return this.origin;
    }

    public String getCity()
    {
        return this.city;
    }


    public Integer getLevel()
    {
        return this.level ;
    }


    public String getDate()
    {
        return this.date;
    }


    public Float getLongitude()
    {
        return this.longitude;
    }
    public Float getLatitude()
    {
        return this.latitude;
    }






}
