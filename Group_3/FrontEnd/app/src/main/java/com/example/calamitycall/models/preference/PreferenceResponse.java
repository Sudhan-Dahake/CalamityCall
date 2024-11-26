package com.example.calamitycall.models.preference;

public class PreferenceResponse {
    private NotificationOn notification_on;
    private Noise noise;
    private Flashing flashing;
    private NotificationAlertType notification_alert_type;
    private TextToSpeech text_to_speech;


    public PreferenceResponse(NotificationOn notification_on, Noise noise, Flashing flashing, NotificationAlertType notification_alert_type, TextToSpeech text_to_speech) {
        this.notification_on = notification_on;
        this.noise = noise;
        this.flashing = flashing;
        this.notification_alert_type = notification_alert_type;
        this.text_to_speech = text_to_speech;
    }


    // setters
    public void setNotificationOn(NotificationOn notification_on) {
        this.notification_on = notification_on;
    }

    public void setNoise(Noise noise) {
        this.noise = noise;
    }

    public void setFlashing(Flashing flashing) {
        this.flashing = flashing;
    }

    public void setNotificationAlertType(NotificationAlertType notification_alert_type) {
        this.notification_alert_type = notification_alert_type;
    }

    public void setTextToSpeech(TextToSpeech text_to_speech) {
        this.text_to_speech = text_to_speech;
    }



    //getters
    public NotificationOn getNotificationOn() {
        return notification_on;
    }

    public Noise getNoise() {
        return noise;
    }

    public Flashing getFlashing() {
        return flashing;
    }

    public NotificationAlertType getNotificationAlertType() {
        return notification_alert_type;
    }

    public TextToSpeech getTextToSpeech() {
        return text_to_speech;
    }
}
