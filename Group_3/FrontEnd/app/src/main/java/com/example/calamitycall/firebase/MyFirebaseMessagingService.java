package com.example.calamitycall.firebase;

import com.example.calamitycall.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenRequest;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import java.util.Map;

import com.example.calamitycall.firebase.FCMData;
import com.example.calamitycall.utils.NotificationConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private NotificationConfig notificationConfig;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Message received: " + remoteMessage.getMessageId());

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        if (!remoteMessage.getData().isEmpty()) {
            FCMData fcmData = new FCMData(
                    remoteMessage.getData().get("notiforigin"),
                    Float.parseFloat(remoteMessage.getData().get("longitude")),
                    Float.parseFloat(remoteMessage.getData().get("latitude")),
                    remoteMessage.getData().get("city"),
                    remoteMessage.getData().get("disastertype"),
                    Integer.parseInt(remoteMessage.getData().get("disasterlevel")),
                    remoteMessage.getData().get("notifdate"),
                    remoteMessage.getData().get("preparationsteps"),
                    remoteMessage.getData().get("activesteps"),
                    remoteMessage.getData().get("recoverysteps"),
                    Boolean.parseBoolean(remoteMessage.getData().get("force_popup"))
            );

            notificationConfig = new NotificationConfig(getApplicationContext());

            notificationConfig.sendNotification(fcmData.getDisasterLevel(), fcmData.getDisasterType(), fcmData.getCity(), fcmData.getNotifOrigin(), fcmData.getLatitude(), fcmData.getLongitude(), fcmData.getPreparationSteps(), fcmData.getActiveSteps(), fcmData.getRecoverySteps());
        }

        /*Map<String, String> data = remoteMessage.getData();

        if (data.containsKey("force_popup") && "true".equals(data.get("force_popup"))) {
            sendPopUpNotification(data);
        }

        else {
            sendPushNotification(data);
        }*/
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.d(TAG, "New token received: " + token);

        // Fetch the device ID
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Send the new token to your backend server to update the stored token for this device
        registerTokenWithServer(token, deviceId);
    }

    private void registerTokenWithServer(String token, String deviceid) {
        // Building the request object
        RegisterTokenRequest request = new RegisterTokenRequest(token, deviceid, null);

        // Get the API Client instance
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<RegisterTokenResponse> call = apiClient.registerToken(request);
        call.enqueue(new Callback<RegisterTokenResponse>() {
            @Override
            public void onResponse(Call<RegisterTokenResponse> call, Response<RegisterTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterTokenResponse registerTokenResponse = response.body();

                    Log.d(TAG, "Status: " + registerTokenResponse.getStatus());
                }

                else {
                    Log.e(TAG, "Failed to register token: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<RegisterTokenResponse> call, Throwable t) {
                Log.e(TAG, "Error registering token with the server: ", t);
            }
        });
    }


    private void sendPushNotification(Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "PUSH_CHANNEL_ID")
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private void sendPopUpNotification(Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "POPUP_CHANNEL_ID")
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(getFullScreenIntent(), true)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private PendingIntent getFullScreenIntent() {
        Intent intent = new Intent(this, MainActivity.class);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
