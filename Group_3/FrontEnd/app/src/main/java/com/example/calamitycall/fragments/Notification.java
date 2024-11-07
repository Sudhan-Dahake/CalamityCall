package com.example.calamitycall.fragments;

import java.util.Date;

public class Notification {

    private String disaster_type;  // like tornado, etc.
    private String origin;
    private String city;
    private Integer level;  // "critical", "urgent", "warning", "watch"
    private Date timeframe;
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
        this.timeframe = new Date();

    }

    public Notification(String disaster_type, Integer level)
    {
        this.disaster_type = disaster_type;
        this.level = level;
    }


    // Constructor
    public Notification(String disaster_type, String origin, String city,  Integer level,  Date timeframe, Float longitude, Float latitude) {
        this.disaster_type = disaster_type;
        this.origin = origin;
        this.city = city;
        this.level = level;
        this.timeframe = timeframe;
        this.longitude = longitude;
        this.latitude = latitude;
    }




    public void ActiveNoitificationSetter(String disaster_type, Integer level)
    {
        this.disaster_type = disaster_type;
        this.level = level;
    }


    public void HistoryNotificationSetter(String disaster_type, Integer level, Date timeframe)
    {
        this.disaster_type = disaster_type;
        this.level = level;
        this.timeframe = timeframe;
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


    public void setDate(Date timestamp)
    {
        this.timeframe = timeframe;
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


    public Date getDate()
    {
        return this.timeframe;
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
