// This Class may or may not be necessary
public class PopupNotification extends Notification {
    // variable of user's device ID
    private String recipientDeviceId;

    // Default Constructor
    public PopupNotification(){
        recipientDeviceId = null;
    }

    // Parameterized Constructor
    public PopupNotification(String RecipientDeviceId){
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
