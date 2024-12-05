package com.example.calamitycall.firebase;

import com.example.calamitycall.MainActivity;
import com.example.calamitycall.SettingsPreferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenRequest;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;
import com.example.calamitycall.models.preference.PreferenceResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.util.Log;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import java.util.Map;

import com.example.calamitycall.firebase.FCMData;
import com.example.calamitycall.utils.NotificationConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private NotificationConfig notificationConfig;

    private TextToSpeech tts;

    private String message;

    private SettingsPreferences settingsPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Message received: " + remoteMessage.getMessageId());

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        this.settingsPreferences = new SettingsPreferences(getApplicationContext());


        // Extract data from the notification
        String disasterType = remoteMessage.getData().get("disastertype");
        String city = remoteMessage.getData().get("city");
        int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

        String severity;

        if (alertLevel == 1) {
            severity = "Watch";
        }

        else if (alertLevel == 2) {
            severity = "warning";
        }

        else if (alertLevel == 3) {
            severity = "urgent";
        }

        else if (alertLevel == 4) {
            severity = "critical";
        }

        else {
            severity = "unknown";
        }

        // Build the TTS message
        message = "This is a " + severity + " alert for " + city + ". A " + disasterType + " has been detected. Please take necessary precautions.";

        // initializeTextToSpeech(message);


        TokenManager tokenManager;

        try {
            tokenManager = TokenManager.getInstance(getApplicationContext());
        }

        catch (Exception e) {
            Log.e(TAG, "TokenManager Initialization Failed", e);

            if (alertLevel == 1) {
                if (settingsPreferences.isWatchNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isWatchTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 2) {
                if (settingsPreferences.isWarningNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isWarningTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 3) {
                if (settingsPreferences.isUrgentNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isUrgentTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 4) {
                if (settingsPreferences.isCriticalNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isCriticalTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else {
                proceedWithNotification(remoteMessage, message);

                initializeTextToSpeech(message);
            }

            return;
        }


        String jwtToken = tokenManager.getAccessToken();

        if (jwtToken != null) {
            Log.d(TAG, "Inside onMessageReceived, calling fetchPreferences");
            fetchPreferences(jwtToken, tokenManager, remoteMessage);
        }

        else {
            Log.d(TAG, "Inside onMessageReceived, calling refreshJWT");
            refreshJWT(tokenManager, remoteMessage);
        }

        /*Map<String, String> data = remoteMessage.getData();

        if (data.containsKey("force_popup") && "true".equals(data.get("force_popup"))) {
            sendPopUpNotification(data);
        }

        else {
            sendPushNotification(data);
        }*/
    }


    private void fetchPreferences(String jwtToken, TokenManager tokenManager, RemoteMessage remoteMessage) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<PreferenceResponse> call = apiClient.getPreferences("Bearer " + jwtToken);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(Call<PreferenceResponse> call, Response<PreferenceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // store preference in memory
                    PreferenceResponse preferenceResponse = response.body();

                    Log.d(TAG, "Preferences Loaded Successfully");



                    int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

                    if (alertLevel == 1) {
                        if (preferenceResponse.getNotificationOn().getWatch()) {
                            proceedWithNotification(remoteMessage, message);

                            if (preferenceResponse.getTextToSpeech().getWatch()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 2) {
                        if (preferenceResponse.getNotificationOn().getWarning()) {
                            proceedWithNotification(remoteMessage, message);

                            if (preferenceResponse.getTextToSpeech().getWarning()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 3) {
                        if (preferenceResponse.getNotificationOn().getUrgent()) {
                            proceedWithNotification(remoteMessage, message);

                            if (preferenceResponse.getTextToSpeech().getUrgent()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 4) {
                        Log.d(TAG, "Critical Alert Sent");
                        if (preferenceResponse.getNotificationOn().getCritical()) {
                            proceedWithNotification(remoteMessage, message);

                            if (preferenceResponse.getTextToSpeech().getCritical()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else {
                        proceedWithNotification(remoteMessage, message);

                        initializeTextToSpeech(message);
                    }




                    //Placeholder for now.
                    // proceedWithNotification(remoteMessage, message);
                }

                else if (response.code() == 401) {
                    // JWT token has expired or invalid, attempt to refresh it.
                    refreshJWT(tokenManager, remoteMessage);
                }
            }

            @Override
            public void onFailure(Call<PreferenceResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching Preferences", t);

                int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

                if (alertLevel == 1) {
                    if (settingsPreferences.isWatchNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isWatchTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 2) {
                    if (settingsPreferences.isWarningNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isWarningTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 3) {
                    if (settingsPreferences.isUrgentNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isUrgentTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 4) {
                    if (settingsPreferences.isCriticalNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isCriticalTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else {
                    proceedWithNotification(remoteMessage, message);

                    initializeTextToSpeech(message);
                }
            }
        });
    }


    private void refreshJWT(TokenManager tokenManager, RemoteMessage remoteMessage) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            Log.d(TAG, "No refresh token available");

            int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

            if (alertLevel == 1) {
                if (settingsPreferences.isWatchNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isWatchTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 2) {
                if (settingsPreferences.isWarningNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isWarningTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 3) {
                if (settingsPreferences.isUrgentNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isUrgentTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else if (alertLevel == 4) {
                if (settingsPreferences.isCriticalNotificationOn()) {
                    proceedWithNotification(remoteMessage, message);

                    if (settingsPreferences.isCriticalTTSOn()) {
                        initializeTextToSpeech(message);
                    }
                }
            }

            else {
                Log.d(TAG, "Default option for Notifications and TTS triggered");

                proceedWithNotification(remoteMessage, message);

                initializeTextToSpeech(message);
            }

            return;
        }

        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<TokenResponse> refreshCall = apiClient.refreshToken(new TokenGenerateRequest(refreshToken));
        refreshCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newAccessToken = response.body().getAccessToken();

                    tokenManager.setAccessToken(newAccessToken);

                    Log.d(TAG, "Inside refreshJWT, calling fetchPreferences");

                    fetchPreferences(newAccessToken, tokenManager, remoteMessage);
                }

                else {
                    Log.d(TAG, "Failed to refresh JWT token: " + response.code());

                    int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

                    if (alertLevel == 1) {
                        if (settingsPreferences.isWatchNotificationOn()) {
                            proceedWithNotification(remoteMessage, message);

                            if (settingsPreferences.isWatchTTSOn()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 2) {
                        if (settingsPreferences.isWarningNotificationOn()) {
                            proceedWithNotification(remoteMessage, message);

                            if (settingsPreferences.isWarningTTSOn()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 3) {
                        if (settingsPreferences.isUrgentNotificationOn()) {
                            proceedWithNotification(remoteMessage, message);

                            if (settingsPreferences.isUrgentTTSOn()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else if (alertLevel == 4) {
                        if (settingsPreferences.isCriticalNotificationOn()) {
                            proceedWithNotification(remoteMessage, message);

                            if (settingsPreferences.isCriticalTTSOn()) {
                                initializeTextToSpeech(message);
                            }
                        }
                    }

                    else {
                        proceedWithNotification(remoteMessage, message);

                        initializeTextToSpeech(message);
                    }
                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Error refreshing JWT Token", t);

                int alertLevel = Integer.parseInt(remoteMessage.getData().get("disasterlevel"));

                if (alertLevel == 1) {
                    if (settingsPreferences.isWatchNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isWatchTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 2) {
                    if (settingsPreferences.isWarningNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isWarningTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 3) {
                    if (settingsPreferences.isUrgentNotificationOn()) {
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isUrgentTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else if (alertLevel == 4) {
                    Log.d("Firebase", "Got a Critical Notification");
                    if (settingsPreferences.isCriticalNotificationOn()) {
                        Log.d("Firebase", "Proceeding...");
                        proceedWithNotification(remoteMessage, message);

                        if (settingsPreferences.isCriticalTTSOn()) {
                            initializeTextToSpeech(message);
                        }
                    }
                }

                else {
                    proceedWithNotification(remoteMessage, message);

                    initializeTextToSpeech(message);
                }
            }
        });
    }


    private void proceedWithNotification(RemoteMessage remoteMessage, String ttsMessage) {
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

            //notificationConfig.sendPopupNotification(fcmData.getDisasterLevel(), fcmData.getDisasterType(), fcmData.getCity(), fcmData.getNotifOrigin(), fcmData.getLatitude(), fcmData.getLongitude(), fcmData.getPreparationSteps(), fcmData.getActiveSteps(), fcmData.getRecoverySteps());
        }
    }


//    private void retryPlayTTS(String ttsMessage) {
//        new Handler(getMainLooper()).postDelayed(() -> {
//            if (isTTSReady && (tts != null)) {
//                tts.speak(ttsMessage, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID");
//                Log.d(TAG, "Retrying TTS Speaking: " + ttsMessage);
//            }
//
//            else {
//                Log.e(TAG, "TTS sill not ready after retry");
//            }
//        }, 500);
//    }


    private void playTextToSpeech(String ttsMessage) {
        if (tts != null) {
            tts.speak(ttsMessage, TextToSpeech.QUEUE_FLUSH, null, "TTS_ID");

            Log.d(TAG, "TTS Speaking: " + ttsMessage);
        }

        else {
            Log.e(TAG, "TextToSpeech instance is null");
        }
    }

    private void initializeTextToSpeech(String ttsMessage) {
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "TTS Language is not supported or missing data.");
                }

                else {
                    Log.d(TAG, "TextToSpeech Initialized successfully");

                    // play the message
                    this.playTextToSpeech(ttsMessage);
                }

            } else {
                Log.e(TAG, "TextToSpeech Initialization Failed");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
