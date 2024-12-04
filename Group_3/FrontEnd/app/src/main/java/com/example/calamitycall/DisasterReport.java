package com.example.calamitycall;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class DisasterReport {
    // Main report fields
    private String report_id;
    private Integer user_id;
    private String created_at;
    private Location location;
    private Event event;
    private String contact;
    private List<Media> media;

    // Constructor to initialize nested objects
    public DisasterReport() {
        this.location = new Location();
        this.event = new Event();
    }

    // Location-related methods
    public void setLocation(double latitude, double longitude, String address) {
        if (location == null) {
            location = new Location();
        }
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAddress(address);
    }

    // Event-related method
    public void setEvent(String type, String severity, String description) {
        if (event == null) {
            event = new Event();
        }
        event.setType(type);
        event.setSeverity(severity);
        event.setDescription(description);
    }

    // Nested Location class
    public static class Location {
        private double latitude;
        private double longitude;
        private String address;

        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    // Nested Event class
    public static class Event {
        private String type;
        private String severity;
        private String description;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    // Nested Media class
    public static class Media {
        private String type;
        private String url;
        private String description;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    // Getters and Setters for main class fields
    public String getReport_id() { return report_id; }
    public void setReport_id(String report_id) { this.report_id = report_id; }
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public List<Media> getMedia() { return media; }
    public void setMedia(List<Media> media) { this.media = media; }

    // JSON Serialization method
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("report_id", report_id);
        json.put("user_id", user_id);
        json.put("created_at", created_at);

        // Location object
        JSONObject locationJson = new JSONObject();
        locationJson.put("latitude", location.getLatitude());
        locationJson.put("longitude", location.getLongitude());
        locationJson.put("address", location.getAddress());
        json.put("location", locationJson);

        // Event object
        JSONObject eventJson = new JSONObject();
        eventJson.put("type", event.getType());
        eventJson.put("severity", event.getSeverity());
        eventJson.put("description", event.getDescription());
        json.put("event", eventJson);

        return json;
    }
}