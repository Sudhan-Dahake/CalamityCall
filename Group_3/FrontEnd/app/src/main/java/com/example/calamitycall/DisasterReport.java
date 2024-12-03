package com.example.calamitycall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DisasterReport {
    private String report_id;
    private Integer user_id;
    private String created_at;
    private Location location;
    private String type;
    private String severity;
    private String description;
    private String contact;
    private List<Media> media;
    public void setLocation(double latitude, double longitude, String address) {
        this.location.setLatitude(latitude);
        this.location.setLongitude(longitude);
        this.location.setAddress(address);
    }

    public void setEventType(String eventType) {
        this.type = eventType;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    // Nested classes to represent Location, Event, and Media
    public static class Location {
        private double latitude;
        private double longitude;
        private String address;

        // Getters and setters
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public static class Event {
        private String type;
        private String severity;
        private String description;

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class Media {
        private String type;
        private String url;
        private String description;

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    // Getters and setters for DisasterReport
    public String getReport_id() { return report_id; }
    public void setReport_id(String report_id) { this.report_id = report_id; }
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public List<Media> getMedia() { return media; }
    public void setMedia(List<Media> media) { this.media = media; }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("report_id", report_id);
        json.put("user_id", user_id);
        json.put("created_at", created_at);

        JSONObject locationJson = new JSONObject();
        locationJson.put("latitude", location.getLatitude());
        locationJson.put("longitude", location.getLongitude());
        locationJson.put("address", location.getAddress());
        json.put("location", locationJson);

        JSONObject eventJson = new JSONObject();
        eventJson.put("type", type);
        eventJson.put("severity", severity);
        eventJson.put("description", description);
        json.put("event", eventJson);

        return json;
    }
}
