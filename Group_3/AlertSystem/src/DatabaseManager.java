import java.util.*;
import java.time.LocalDate;

// Class that defines the functionality for saving and getting data from the supabas db
public class DatabaseManager {
    // connection variable for the database
    /*private NotificationModel dbConnection;*/

    // Default Constructor
    public DatabaseManager(){
        /*dbConnection = null;*/
    }

    // Parameterized constructor
    //public DatabaseManager(/*NotificationModel connection */){
        /*dbConnection = connection;*/
    //}

    // Stores Notification into the database
    public void storeNotificationData(Notification notif){
        // Call CreateNotification(self, **NotifParams: dict[str, any])
    }
    

    // Stores user information when signing up
    public void storeUserData(UserAccount user){
        // Call function to save into user db table
    }
 
    // Updates user preferences
    public void updateUserPreferences(Preferences userPrefereces, int accountId){
        // call function to save into preferences db table
    }

    // Gets 1 Notification from the database
    public Notification getNotificationData(){

        // Get 1 notification
        return new Notification();
    }

    // Gets a list of Notifications from the database
    public ArrayList<Notification> getNotificationHistory(LocalDate timeFrame){

        // get list of notifications (based on timeframe is a should goal)
        ArrayList<Notification> notifs = new ArrayList<>();
        return notifs;
    }
    // Stores user information when signing up
    public UserAccount getUserData(){
        // Call function to get user from db table
        return new UserAccount();
    }
 
    // Updates user preferences
    public Preferences getUserPreferences(){
        // call function to get preferences from db table
        return new Preferences();
    }

}
