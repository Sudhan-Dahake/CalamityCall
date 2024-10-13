import java.time.LocalDate;

// Class that defines the Notification information
public class Notification {

    // Notification properties
    private String id;
    private String origin;
    private String disasterType;
    private DisasterLevel disasterLevel;
    private NotificationType notificationType;
    private LocalDate dateReceived;
    private String location;
    private Float[] coordinates;

    // Default Constructor
    public Notification(){
        id = null;
        origin = null;
        disasterType = null;
        disasterLevel = null;
        notificationType = null;
        dateReceived = null;
        location = null;
        coordinates = null;
    }

    // Parameterized Constructor
    public Notification(String Id, String Origin, String DisasterType, DisasterLevel DisasterLevel,
                        NotificationType NotificationType, LocalDate DateReceived, String Location, Float[] Coordinates){
        id = Id;
        origin = Origin;
        disasterType = DisasterType;
        disasterLevel = DisasterLevel;
        notificationType = NotificationType;
        dateReceived = DateReceived;
        location = Location;
        coordinates = Coordinates;
    }

    // GETTERS
    public String getId(){
        return id;
    }

    public String getOrigin(){
        return origin;
    }

    public String getDisasterType(){
        return disasterType;
    }

    public DisasterLevel getDisasterLevel(){
        return disasterLevel;
    }

    public NotificationType getNotificationType(){
        return notificationType;
    }

    public LocalDate getDateRecieved(){
        return dateReceived;
    }

    public String getLocation(){
        return location;
    }

    public Float[] getCoordinates(){
        return coordinates;
    }

    // SETTERS
    public void setId(String Id){
        id = Id;
    }

    public void setOrigin(String Origin){
        origin = Origin;
    }

    public void setDisasterType(String DisasterType){
        disasterType = DisasterType;
    }

    public void setDisasterLevel(DisasterLevel DisasterLevel){
        disasterLevel = DisasterLevel;
    }

    public void setNotificationType(NotificationType NotificationType){
        notificationType = NotificationType;
    }

    public void setDateRecieved(LocalDate DateRecieved){
        dateReceived = DateRecieved;
    }

    public void setLocation(String Location){
        location = Location;
    }

    public void setCoordinates(Float[] Coordinates){
        coordinates = Coordinates;
    }

}
