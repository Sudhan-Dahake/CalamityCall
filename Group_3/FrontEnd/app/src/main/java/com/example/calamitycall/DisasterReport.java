package com.example.calamitycall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DisasterReport {
    private String reportId;
    private String userId;
    private String createdAt;
    private Location location;
    private String eventType;
    private String severity;
    private String description;
    private String contact;
    private List<Media> media;

    public void setLocation(double latitude, double longitude) {
        this.location.setLatitude(latitude);
        this.location.setLongitude(longitude);
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public List<Media> getMedia() { return media; }
    public void setMedia(List<Media> media) { this.media = media; }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("report_id", reportId);
        json.put("user_id", userId);
        json.put("created_at", createdAt);

        JSONObject locationJson = new JSONObject();
        locationJson.put("latitude", location.latitude);
        locationJson.put("longitude", location.longitude);
        json.put("location", locationJson);

        JSONObject eventJson = new JSONObject();
        eventJson.put("type", eventType);
        eventJson.put("severity", severity);
        eventJson.put("description", description);
        json.put("event", eventJson);

        return json;
    }
}
