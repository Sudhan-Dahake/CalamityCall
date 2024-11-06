package com.example.calamitycall.Notif_Management;
// This class would handle the retrieval of the data from NWS and ES
// This would include the 2 endpoints for each of the APIs if its possible to put into 1 class
// if its not possible to do in 1 class this will be segregated into 2 folders (NWS_Endpoint, ES_Endpoint)
import android.util.Log;

import androidx.annotation.NonNull;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Class that handles the Endpoints for NWS and ES alert retrieval
@Path("/Notifications")
public class DataManager {

    // needed properties to manage data coming into the app (may not be needed as this class is called by other services outside our project)
    /*private final ApiService apiService;
    private final NotificationManager notifManager;

    // Parameterized Constructor
    public DataManager(ApiService ApiService, NotificationManager NotifManager){
        apiService = ApiService;
        notifManager = NotifManager;
    }*/

    // Endpoint for NWS Notification Data
    @POST
    @Path("/NWSAlert")
    public void handle_NWSalert_Event(NotificationRequest req){

        // When this endpoint is called, call the database's save Notification API
        Call<NotificationResponse> call = RetrofitInstance.getRetrofitInstance().create(ApiService.class).incomingNotification(req);

        // Run it asynchronously
        call.enqueue(
                new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationResponse> call,
                                           @NonNull Response<NotificationResponse> response) {
                        // Alert notification system if successful, or just log message (could also display error to UI)
                        if (response.isSuccessful()) {
                            Log.d("Notif", "Saved Notification, Alerting system...");
                            new NotificationManager(RetrofitInstance.getRetrofitInstance().create(ApiService.class)).newNotificationAlert();
                        }else {
                            Log.d("Notif", "Response unsuccessful. Code: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<NotificationResponse> call,
                                          @NonNull Throwable t) {
                        Log.d("Notif", "Failed to save Notification");
                    }
                }
        );
    }

    // Endpoint for ES Notification Data
    @POST
    @Path("/ESAlert")
    public void handle_ESalert_Event(NotificationRequest req){

        // When this endpoint is called, call the database's save Notification API
        Call<NotificationResponse> call = RetrofitInstance.getRetrofitInstance().create(ApiService.class).incomingNotification(req);

        // Run it asynchronously
        call.enqueue(
                new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationResponse> call,
                                           @NonNull Response<NotificationResponse> response) {
                        // Alert notification system if successful, or just log message (could also display error to UI)
                        if (response.isSuccessful()) {
                            Log.d("Notif", "Saved Notification, Alerting system...");
                            new NotificationManager(RetrofitInstance.getRetrofitInstance().create(ApiService.class)).newNotificationAlert();
                        }else {
                            Log.d("Notif", "Response unsuccessful. Code: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<NotificationResponse> call,
                                          @NonNull Throwable t) {
                        Log.d("Notif", "Failed to save Notification");
                    }
                }
        );
    }
}