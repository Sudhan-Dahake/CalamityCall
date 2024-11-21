package com.example.calamitycall.models.preference;

public class TextToSpeech {
    private int preferenceid;
    private boolean watch;
    private boolean warning;
    private boolean urgent;
    private boolean critical;


    public TextToSpeech(int preferenceid, boolean watch, boolean warning, boolean urgent, boolean critical) {
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

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }



    //getters
    public int getPreferenceid() {
        return this.preferenceid;
    }

    public boolean getWatch() {
        return this.watch;
    }

    public boolean getWarning() {
        return this.warning;
    }

    public boolean getUrgent() {
        return this.urgent;
    }

    public boolean getCritical() {
        return this.critical;
    }
}
