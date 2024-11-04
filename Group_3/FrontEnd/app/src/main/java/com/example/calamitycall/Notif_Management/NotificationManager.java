package com.example.calamitycall.Notif_Management;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.calamitycall.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Class that defines the functions to send and retrieve notifications to the users when alerted of new notifications
public class NotificationManager {
    // needed properties to manage notifications
    private final ApiService apiService;
    private NotificationHistoryResponse notifs;


    // Default Constructor
    public NotificationManager(ApiService ApiService){
        apiService = ApiService;
    }

    // Function to be alerted of new notifications and send to users
    public void newNotificationAlert(){

        // call the database API to retrieve the stored Notification
        Call<NotificationRequest> call = this.apiService.getNotification();

        // Run it asynchronously
        call.enqueue(
                new Callback<NotificationRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationRequest> call,
                                           @NonNull Response<NotificationRequest> response) {
                        // Send Notification to UI if successful, or just log message (could also display error to UI)
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("Notif", "Notification Received");
                            sendNotification(response.body());
                        }else {
                            Log.d("Notif", "Response unsuccessful or body is null. Code: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<NotificationRequest> call,
                                          @NonNull Throwable t) {
                        Log.d("Notif", "Failed to get Notification");
                    }
                }
        );
    }

    // Function to send notification information to users
    public void sendNotification(NotificationRequest notif){
        // call function to show notification through UI
    }

    // Function to retrieve a history of past notifications and process it for the users
    public void getNotificationHistory(String timeFrame, View view){

        // Call the database API to get the history of Notifications based on the timeFrame
        Call<NotificationHistoryResponse> call = this.apiService.getNotificationHistory(timeFrame);

        // Run it asynchronously
        call.enqueue(new Callback<NotificationHistoryResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<NotificationHistoryResponse> call,
                                           @NonNull Response<NotificationHistoryResponse> response) {
                        // Find the TextView by its ID
                        TextView textViewHistory = view.findViewById(R.id.textViewHistory);

                        // Send History to UI if successful, or just log message (could also display error to UI)
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("Notif", "History received");
                            notifs = response.body();


                            textViewHistory.setText("History Found!"); // instead of this, pass the list of notifications to the UI


                        }else {
                            Log.d("Notif", "Response unsuccessful or body is null. Code: " + response.code());
                            textViewHistory.setText("History Not Found...");
                        }
                    }
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Call<NotificationHistoryResponse> call,
                                          @NonNull Throwable t) {
                        // Find the TextView by its ID
                        TextView textViewHistory = view.findViewById(R.id.textViewHistory);
                        Log.d("Notif", "Failed to get History");
                        textViewHistory.setText("Error Fetching History...");
                    }
                }
        );
    }
}
