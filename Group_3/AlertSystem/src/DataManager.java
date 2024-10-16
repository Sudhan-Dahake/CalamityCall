// This class would handle the retrieval of the data from NWS and ES
// This would include the 2 endpoints for each of the APIs if its possible to put into 1 class
// if its not possible to do in 1 class this will be segregated into 2 folders (NWS_Endpoint, ES_Endpoint)
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

// Class that handles the Endpoints for NWS and ES alert retrieval
@Path("/Notifications")
public class DataManager {

    // variable that contains the notification information to pass out
    private Notification notifData;

    // Default Constructor
    public DataManager(){
        notifData = new Notification();
    }

    // GETTER
    public Notification getNotifData(){
        return notifData;
    }

    // SETTER
    public void setNotifData(Notification NotifData){
        notifData = NotifData;
    }

    // Endpoint for NWS Notification Data
    @POST
    @Path("/NWSAlert")
    public Response handle_NWSalert_Event(){

        // Process information and put into Notification object
        // Call storeNotificationData(notif)
        // Call newNotificationAlert()

        return Response.status(200).build();
    }

    // Endpoint for ES Notification Data
    @POST
    @Path("/ESAlert")
    public Response handle_ESalert_Event(){

        // Process information and put into Notification object
        // Call storeNotificationData(notif)
        // Call newNotificationAlert()
        return Response.status(200).build();
    }
}