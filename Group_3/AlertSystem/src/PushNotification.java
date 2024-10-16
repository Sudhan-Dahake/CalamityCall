// This Class may or may not be necessary
public class PushNotification extends Notification {
    // variable of user's device ID
    private String recipientDeviceId;

    // Default Constructor
    public PushNotification(){
        recipientDeviceId = null;
    }

    // Parameterized Constructor
    public PushNotification(String RecipientDeviceId){
        recipientDeviceId = RecipientDeviceId;
    }

    // GETTER
    public String getRecipientDeviceId(){
        return recipientDeviceId;
    }

    // SETTER
    public void setRecipientDeviceId(String RecipientDeviceId){
        recipientDeviceId = RecipientDeviceId;
    }
}