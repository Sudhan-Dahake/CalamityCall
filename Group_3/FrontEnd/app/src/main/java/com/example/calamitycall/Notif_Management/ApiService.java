package com.example.calamitycall.Notif_Management;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Class that defines the API Endpoint calls for the database APIs
public interface ApiService {

    // Save Notification to db
    @POST("/notifications/")
    @Headers("Content-Type: application/json")
    Call<NotificationResponse> incomingNotification(@Body NotificationRequest notificationRequest);

    // Get Notification History
    @GET("/notifications/history")
    @Headers("Content-Type: application/json")
    Call<NotificationHistoryResponse> getNotificationHistory(@Query("timeframe") String timeframe);

    // Get latest Notification
    @GET("/notifications/immediate")
    @Headers("Content-Type: application/json")
    Call<NotificationRequest> getNotification();


    // Update (Save) User Preferences
    @POST("/preferences/update")
    @Headers("Content-Type: application/json")
    Call<PreferencesResponse> updatePreferences(@Body PreferencesRequest preferencesRequest);

    // Get User Preferences
    @GET("/preferences/get")
    @Headers("Content-Type: application/json")
    Call<PreferencesRequest> getPreferences();
}