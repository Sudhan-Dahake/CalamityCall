import java.time.LocalDate;
import java.util.*;

// Class that defines the functions to send and retrieve notifications to the users when alerted of new notifications
public class NotificationManager {
    
    // Default Constructor
    public NotificationManager(){}

    // Function to be alerted of new notifications and send to users
    public void newNotificationAlert(){
        // call get notification function from dbmanager
        // call send notification function
    }

    // Function to send notification information to users
    public void sendNotification(Notification notif){
        // send notification to users
    }

    // Function to retrieve a history of past notifications and process it for the users
    public ArrayList<Notification> getNotificationHistory(LocalDate timeFrame){
        // get list of notifications
        ArrayList<Notification> notifs = new ArrayList<>();
        return notifs;
    }
}
