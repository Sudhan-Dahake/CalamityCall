package com.example.calamitycall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;
import com.example.calamitycall.models.preference.NotificationOn;
import com.example.calamitycall.models.preference.PreferenceUpdateRequest;
import com.example.calamitycall.models.preference.PreferenceUpdateResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationOnActivity extends AppCompatActivity {
    private static final String TAG = "NotificationOnActivity";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch watchSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch warningSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch urgentSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch criticalSwitch;

    private SharedPreferences sharedPreferences;
    TextView SavedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_on_page);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        // Find UI elements
        watchSwitch = findViewById(R.id.watch_switch);
        warningSwitch = findViewById(R.id.warning_switch);
        urgentSwitch = findViewById(R.id.urgent_switch);
        criticalSwitch = findViewById(R.id.critical_switch);
        TextView settings = findViewById(R.id.settings_title);
        // Save button
        Button saveButton = findViewById(R.id.notificationon_save);
        SavedText = findViewById(R.id.SavedText);


        // Load saved preferences
        loadPreferences();

        watchSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        // Set up the settings button listener
        settings.setOnClickListener(v -> {
            // Start fragment transaction to open settings page
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new SettingsPage());
            transaction.commit();
            finish(); // Close current activity
        });

        // Set up save button listener
        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
    }

    private void loadPreferences() {
        // Load switch states from SharedPreferences
        watchSwitch.setChecked(sharedPreferences.getBoolean("watch_notification_on", true));
        warningSwitch.setChecked(sharedPreferences.getBoolean("warning_notification_on", true));
        urgentSwitch.setChecked(sharedPreferences.getBoolean("urgent_notification_on", true));
        criticalSwitch.setChecked(sharedPreferences.getBoolean("critical_notification_on", true));
    }

    private void savePreferences() {
        String tableName = "notification_on";
        // Get Switch states
        boolean watch = watchSwitch.isChecked();
        boolean warning = warningSwitch.isChecked();
        boolean urgent = urgentSwitch.isChecked();
        boolean critical = criticalSwitch.isChecked();

        // Create NotificationOn Feature
        NotificationOn notificationOn = new NotificationOn(watch, warning, urgent, critical);

        PreferenceUpdateRequest<Object> preferenceUpdateRequest = new PreferenceUpdateRequest<>(
                tableName,
                notificationOn
        );


        TokenManager tokenManager;

        try {
            tokenManager = TokenManager.getInstance(getApplicationContext());
        }

        catch (Exception e) {
            Log.e(TAG, "TokenManager Initialization Failed", e);

            return;
        }

        String jwtToken = tokenManager.getAccessToken();

        if (jwtToken != null) {
            Log.d(TAG, "Inside savePreferences -> jwtToken is not null, calling the update preference endpoint");

            updatePreferences(jwtToken, tokenManager, preferenceUpdateRequest);
        }

        else {
            Log.d(TAG, "Inside savePreferences, getting new JWT using refresh token");

            refreshJWT(tokenManager, preferenceUpdateRequest);
        }


        // Save switch states to SharedPreferences
        new Thread(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("watch_notification_on", watchSwitch.isChecked());
            editor.putBoolean("warning_notification_on", warningSwitch.isChecked());
            editor.putBoolean("urgent_notification_on", urgentSwitch.isChecked());
            editor.putBoolean("critical_notification_on", criticalSwitch.isChecked());
            editor.apply();

            // update the UI on the main thread
            runOnUiThread(() -> SavedText.setVisibility(View.VISIBLE));
        }).start();
    }


    private void updatePreferences(String jwtToken, TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        // Initialize Retrofit and ApiClient
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        // Make the API Call to save preferences
        Call<PreferenceUpdateResponse> notificationOnCall = apiClient.updateNotificationOn("Bearer " + jwtToken, preferenceUpdateRequest);

        notificationOnCall.enqueue(new Callback<PreferenceUpdateResponse>() {
            @Override
            public void onResponse(Call<PreferenceUpdateResponse> call, Response<PreferenceUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PreferenceUpdateResponse serverResponse = response.body();

                    Log.d("NotificationOn", "Server Response for NotificationOn Updation: " + serverResponse);
                }

                else {
                    SavedText.setVisibility(View.VISIBLE);
                    SavedText.setText("Failed to Save Preferences!!");
                }
            }

            @Override
            public void onFailure(Call<PreferenceUpdateResponse> call, Throwable t) {
                SavedText.setVisibility(View.VISIBLE);
                SavedText.setText("Error Saving Preferences!!");
            }
        });
    }


    private void refreshJWT(TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            Log.d(TAG, "No refresh token available");

            Intent intent = new Intent(NotificationOnActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Initialize Retrofit and ApiClient
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<TokenResponse> refreshCall = apiClient.refreshToken(new TokenGenerateRequest(refreshToken));
        refreshCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newAccessToken = response.body().getAccessToken();

                    tokenManager.setAccessToken(newAccessToken);

                    Log.d(TAG, "Inside refreshJWT, calling fetchPreferences");

                    updatePreferences(newAccessToken, tokenManager, preferenceUpdateRequest);
                }

                else {
                    Log.d(TAG, "Failed to refresh JWT token: " + response.code());

                    Intent intent = new Intent(NotificationOnActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error refreshing JWT Token", t);

                Intent intent = new Intent(NotificationOnActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}