package com.example.calamitycall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calamitycall.FlashingActivity;
import com.example.calamitycall.NoiseActivity;
import com.example.calamitycall.NotificationOnActivity;
import com.example.calamitycall.NotificationTypeActivity;
import com.example.calamitycall.R;
import com.example.calamitycall.SettingsPreferences;
import com.example.calamitycall.TexttospeechActivity;
import com.example.calamitycall.models.preference.PreferenceResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenCallback;
import com.example.calamitycall.utils.TokenManager;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsPage extends Fragment {

    private View view;
    private Button notificationOnButton;
    private Button notificationAlertTypeButton;
    private Button flashingButton;
    private Button noiseButton;
    private Button texttospeechButton;

    private SettingsPreferences settingsPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate a placeholder view initially
        view = inflater.inflate(R.layout.activity_settings_page, container, false);
        // Initialize buttons and listeners
        initializeButtons(view);


//        this.settingsPreferences = new SettingsPreferences(requireContext());

//        tokenStuff(() -> {
//            // After tokenStuff completes, inflate the actual layout
//            requireActivity().runOnUiThread(() -> {
//                // Inflate the main settings layout
//                View settingsView = inflater.inflate(R.layout.activity_settings_page, container, false);
//
//                // Replace the placeholder view with the settings view
//                ViewGroup parent = (ViewGroup) view.getParent();
//                if (parent != null) {
//                    int index = parent.indexOfChild(view);
//                    parent.removeView(view);
//                    parent.addView(settingsView, index);
//                }
//
//                // Update the reference to the new view
//                view = settingsView;
//
//                // Initialize buttons and listeners
//                initializeButtons(settingsView);
//            });
//        });

        return view;
    }


    private void initializeButtons(View settingsView) {
        // Initialize buttons
        notificationOnButton = view.findViewById(R.id.notification_on);
        notificationAlertTypeButton = view.findViewById(R.id.notification_alert_type);
        flashingButton = view.findViewById(R.id.flashing);
        noiseButton = view.findViewById(R.id.noise);
        texttospeechButton = view.findViewById(R.id.text_to_speech);

        // Set click listeners for each button
        notificationOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationOnActivity.class);
            }
        });

        notificationAlertTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationTypeActivity.class);
            }
        });

        flashingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FlashingActivity.class);
            }
        });

        noiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NoiseActivity.class);
            }
        });

        texttospeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TexttospeechActivity.class);
            }
        });
    }

    // Helper method to open an activity safely
    private void openActivity(Class<?> activityClass) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), activityClass);
            startActivity(intent);
        }
    }


    private void tokenStuff(TokenCallback callback) {
        TokenManager tokenManager;

        try {
            tokenManager = TokenManager.getInstance(requireContext());
        }

        catch (Exception e) {
            //Log.e(TAG, "TokenManager Initialization Failed", e);
            callback.onComplete(); // Notify completion if initialization fails

            return;
        }


        String jwtToken = tokenManager.getAccessToken();

        if (jwtToken != null) {
            // Log.d(TAG, "Inside onMessageReceived, calling fetchPreferences");
            fetchPreferences(jwtToken, tokenManager, callback);
        }

        else {
            // Log.d(TAG, "Inside onMessageReceived, calling refreshJWT");
            refreshJWT(tokenManager, callback);
        }
    }



    private void refreshJWT(TokenManager tokenManager, TokenCallback callback) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            // Log.d(TAG, "No refresh token available");

            callback.onComplete(); // Notify completion if refresh token is unavailable

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

                    //Log.d(TAG, "Inside refreshJWT, calling fetchPreferences");

                    fetchPreferences(newAccessToken, tokenManager, callback);
                }

                else {
                    //Log.d(TAG, "Failed to refresh JWT token: " + response.code());

                    callback.onComplete(); // Notify completion
                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                // Log.e(TAG, "Error refreshing JWT Token", t);

                callback.onComplete(); // Notify completion
            }
        });
    }



    private void fetchPreferences(String jwtToken, TokenManager tokenManager, TokenCallback callback) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<PreferenceResponse> call = apiClient.getPreferences("Bearer " + jwtToken);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(Call<PreferenceResponse> call, Response<PreferenceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // store preference in memory
                    PreferenceResponse preferenceResponse = response.body();

                    // Log.d(TAG, "Preferences Loaded Successfully");

                    SettingsPage.this.settingsPreferences.setWatchNotificationOn(preferenceResponse.getNotificationOn().getWatch());
                    SettingsPage.this.settingsPreferences.setWarningNotificationOn(preferenceResponse.getNotificationOn().getWarning());
                    SettingsPage.this.settingsPreferences.setUrgentNotificationOn(preferenceResponse.getNotificationOn().getUrgent());
                    SettingsPage.this.settingsPreferences.setCriticalNotificationOn(preferenceResponse.getNotificationOn().getCritical());

                    SettingsPage.this.settingsPreferences.setWatchFlashingOn(preferenceResponse.getFlashing().getWatch());
                    SettingsPage.this.settingsPreferences.setWarningFlashingOn(preferenceResponse.getFlashing().getWarning());
                    SettingsPage.this.settingsPreferences.setUrgentFlashingOn(preferenceResponse.getFlashing().getUrgent());
                    SettingsPage.this.settingsPreferences.setCriticalFlashingOn(preferenceResponse.getFlashing().getCritical());

                    SettingsPage.this.settingsPreferences.setWatchNoiseOn(preferenceResponse.getNoise().getWatch());
                    SettingsPage.this.settingsPreferences.setWarningNoiseOn(preferenceResponse.getNoise().getWarning());
                    SettingsPage.this.settingsPreferences.setUrgentNoiseOn(preferenceResponse.getNoise().getUrgent());
                    SettingsPage.this.settingsPreferences.setCriticalNoiseOn(preferenceResponse.getNoise().getCritical());

                    SettingsPage.this.settingsPreferences.setWatchNotificationType(preferenceResponse.getNotificationAlertType().getWatch());
                    SettingsPage.this.settingsPreferences.setWarningNotificationType(preferenceResponse.getNotificationAlertType().getWarning());
                    SettingsPage.this.settingsPreferences.setUrgentNotificationType(preferenceResponse.getNotificationAlertType().getUrgent());
                    SettingsPage.this.settingsPreferences.setCriticalNotificationType(preferenceResponse.getNotificationAlertType().getCritical());

                    SettingsPage.this.settingsPreferences.setWatchTTSOn(preferenceResponse.getTextToSpeech().getWatch());
                    SettingsPage.this.settingsPreferences.setWarningTTSOn(preferenceResponse.getTextToSpeech().getWarning());
                    SettingsPage.this.settingsPreferences.setUrgentTTSOn(preferenceResponse.getTextToSpeech().getUrgent());
                    SettingsPage.this.settingsPreferences.setCriticalTTSOn(preferenceResponse.getTextToSpeech().getCritical());

                    //Placeholder for now.
                    // proceedWithNotification(remoteMessage, message);
                }

                else if (response.code() == 401) {
                    // JWT token has expired or invalid, attempt to refresh it.
                    refreshJWT(tokenManager, callback);
                }

                callback.onComplete(); // Notify completion
            }

            @Override
            public void onFailure(Call<PreferenceResponse> call, Throwable t) {
                //Log.e(TAG, "Error fetching Preferences", t);

                callback.onComplete(); // Notify completion
            }
        });
    }
}
