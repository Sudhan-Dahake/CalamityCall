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
import com.example.calamitycall.models.preference.Noise;
import com.example.calamitycall.models.preference.PreferenceUpdateRequest;
import com.example.calamitycall.models.preference.PreferenceUpdateResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class NoiseActivity extends AppCompatActivity {
    private static final String TAG = "NoiseActivity";

    private Switch noiseSwitch; // Main switch for watch noise notifications
    private Switch warningNoiseSwitch;
    private Switch urgentNoiseSwitch;
    private Switch criticalNoiseSwitch;
    private SharedPreferences sharedPreferences;
    TextView SavedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_noise_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        // Find UI elements
        noiseSwitch = findViewById(R.id.watch_switch);
        warningNoiseSwitch = findViewById(R.id.warning_switch);
        urgentNoiseSwitch = findViewById(R.id.urgent_switch);
        criticalNoiseSwitch = findViewById(R.id.critical_switch);
        TextView settings = findViewById(R.id.settings_title);
        // Save button
        Button saveButton = findViewById(R.id.noise_save);
        SavedText = findViewById(R.id.SavedText);


        // Load saved preferences
        loadPreferences();

        noiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(v -> {
            // Begin the fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace the fragment in the container with the SettingsPage fragment
            transaction.replace(R.id.fragment_container, new SettingsPage());

            // Commit the transaction
            transaction.commit();
            finish(); // This will close the FlashingActivity
        });


        saveButton.setOnClickListener(v -> savePreferences());
    }


    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
    }

    private void loadPreferences() {
        noiseSwitch.setChecked(sharedPreferences.getBoolean("watch_noise", true));
        warningNoiseSwitch.setChecked(sharedPreferences.getBoolean("warning_noise", true));
        urgentNoiseSwitch.setChecked(sharedPreferences.getBoolean("urgent_noise", true));
        criticalNoiseSwitch.setChecked(sharedPreferences.getBoolean("critical_noise", true));
    }

    private void savePreferences() {
        String tableName = "noise";
        boolean watch = noiseSwitch.isChecked();
        boolean warning = warningNoiseSwitch.isChecked();
        boolean urgent = urgentNoiseSwitch.isChecked();
        boolean critical = criticalNoiseSwitch.isChecked();

        Noise noise = new Noise(watch, warning, urgent, critical);
        PreferenceUpdateRequest<Object> preferenceUpdateRequest = new PreferenceUpdateRequest<>(tableName, noise);

        TokenManager tokenManager;

        try {
            tokenManager = TokenManager.getInstance(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "TokenManager Initialization Failed", e);
            return;
        }

        String jwtToken = tokenManager.getAccessToken();

        if (jwtToken != null) {
            Log.d(TAG, "Inside savePreferences -> jwtToken is not null, calling the update preference endpoint");
            updatePreferences(jwtToken, tokenManager, preferenceUpdateRequest);
        } else {
            Log.d(TAG, "Inside savePreferences, getting new JWT using refresh token");
            refreshJWT(tokenManager, preferenceUpdateRequest);
        }

        // Save switch states to SharedPreferences
        new Thread(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("watch_noise", noiseSwitch.isChecked());
            editor.putBoolean("warning_noise", warningNoiseSwitch.isChecked());
            editor.putBoolean("urgent_noise", urgentNoiseSwitch.isChecked());
            editor.putBoolean("critical_noise", criticalNoiseSwitch.isChecked());
            editor.apply();

            runOnUiThread(() -> SavedText.setVisibility(View.VISIBLE));
        }).start();
    }

    private void updatePreferences(String jwtToken, TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
        Call<PreferenceUpdateResponse> updateCall = apiClient.updateNotificationOn("Bearer " + jwtToken, preferenceUpdateRequest);

        updateCall.enqueue(new Callback<PreferenceUpdateResponse>() {
            @Override
            public void onResponse(Call<PreferenceUpdateResponse> call, Response<PreferenceUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PreferenceUpdateResponse serverResponse = response.body();
                    Log.d(TAG, "Server Response for Noise Preference Updation: " + serverResponse);
                } else {
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
            startActivity(new Intent(NoiseActivity.this, LoginActivity.class));
            finish();
        }

        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
        Call<TokenResponse> refreshCall = apiClient.refreshToken(new TokenGenerateRequest(refreshToken));

        refreshCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newAccessToken = response.body().getAccessToken();
                    tokenManager.setAccessToken(newAccessToken);
                    updatePreferences(newAccessToken, tokenManager, preferenceUpdateRequest);
                } else {
                    startActivity(new Intent(NoiseActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Error refreshing JWT Token", t);
                startActivity(new Intent(NoiseActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}