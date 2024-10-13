import java.time.LocalDate;

public class Preferences {
    private String notificationType;
    private String disasterType;
    private int severityType;
    private Boolean notifFlashing;
    private Boolean textToSpeech;
    private LocalDate notifTimeFrame;

    public Preferences(){
        notificationType = null;
        disasterType = null;
        severityType = 0;
        notifFlashing = null;
        textToSpeech = null;
        notifTimeFrame = null;
    }

    public Preferences(String NotificationType, String DisasterType, int SeverityType, Boolean NotifFlashing, 
                        Boolean TextToSpeech, LocalDate NotifTimeFrame){
        notificationType = NotificationType;
        disasterType = DisasterType;
        severityType = SeverityType;
        notifFlashing = NotifFlashing;
        textToSpeech = TextToSpeech;
        notifTimeFrame = NotifTimeFrame;
    }

    // GETTERS
    public String getNotificationType(){
        return notificationType;
    }
    public String getDisasterType(){
        return disasterType;
    }
    public int getSeverityType(){
        return severityType;
    }
    public Boolean getNotifFlashing(){
        return notifFlashing;
    }
    public Boolean getTextToSpeech(){
        return textToSpeech;
    }
    public LocalDate getNotifTimeFrame(){
        return notifTimeFrame;
    }

    // SETTERS
    public void setNotificationType(String NotificationType){
        notificationType = NotificationType;
    }
    public void setDisasterType(String DisasterType){
        disasterType = DisasterType;
    }
    public void setSeverityType(int SeverityType){
        severityType = SeverityType;
    }
    public void setNotifFlashing(Boolean NotifFlashing){
        notifFlashing = NotifFlashing;
    }
    public void setTextToSpeech(Boolean TextToSpeech){
        textToSpeech = TextToSpeech;
    }
    public void setNotifTimeFrame(LocalDate NotifTimeFrame){
        notifTimeFrame = NotifTimeFrame;
    }


    public void savePreferences(int AccountId){
        // Call db manager save preferences function
    }
}
