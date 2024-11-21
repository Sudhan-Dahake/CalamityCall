package com.example.calamitycall.models.preference;

public class NotificationAlertType {
    private int preferenceid;
    private String watch;
    private String warning;
    private String urgent;
    private String critical;

    public NotificationAlertType(int preferenceid, String watch, String warning, String urgent, String critical) {
        this.preferenceid = preferenceid;
        this.watch = watch;
        this.warning = warning;
        this.urgent = urgent;
        this.critical = critical;
    }


    //setters
    public void setPreferenceid(int preferenceid) {
        this.preferenceid = preferenceid;
    }

    public void setWatch(String watch) {
        this.watch = watch;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }



    //getters
    public int getPreferenceid() {
        return this.preferenceid;
    }

    public String getWatch() {
        return this.watch;
    }

    public String getWarning() {
        return this.warning;
    }

    public String getUrgent() {
        return this.urgent;
    }

    public String getCritical() {
        return this.critical;
    }
}
