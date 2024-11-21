package com.example.calamitycall.models.NotificationHistory;

public class NotificationHistoryRequest {
    private String timeFrame;

    public NotificationHistoryRequest(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame
    }

    public String getTimeFrame() {
        return timeFrame;
    }
}
