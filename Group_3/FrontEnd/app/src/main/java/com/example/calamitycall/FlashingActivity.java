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
import com.example.calamitycall.models.preference.Flashing;
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

public class FlashingActivity extends AppCompatActivity {
    private static final String TAG = "FlashingActivity";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch flashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch warningFlashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch urgentFlashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch criticalFlashSwitch;

    private SharedPreferences sharedPreferences;

    TextView SavedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_flashing_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sharedPreferences = getSharedPreferences("FlashingPreferences", MODE_PRIVATE);

        flashSwitch = findViewById(R.id.watch_switch);
        warningFlashSwitch = findViewById(R.id.warning_switch);
        urgentFlashSwitch = findViewById(R.id.urgent_switch);
        criticalFlashSwitch = findViewById(R.id.critical_switch);
        TextView settings = findViewById(R.id.settings_title);
        SavedText = findViewById(R.id.SavedText);
        // New save button
        Button saveButton = findViewById(R.id.flashing_save);  // Initialize save button

        loadPreferences();

        flashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(v -> {
            // Begin the fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace the fragment in the container with the SettingsPage fragment
            transaction.replace(R.id.fragment_container, new SettingsPage());

            // Commit the transaction
            transaction.commit();
            finish(); // This will close the FlashingActivity
        });

        // Set listener for save button
        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void loadPreferences() {
        flashSwitch.setChecked(sharedPreferences.getBoolean("flash", true));
        warningFlashSwitch.setChecked(sharedPreferences.getBoolean("warning_flash", true));
        urgentFlashSwitch.setChecked(sharedPreferences.getBoolean("urgent_flash", true));
        criticalFlashSwitch.setChecked(sharedPreferences.getBoolean("critical_flash", true));
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
    }
    private void savePreferences() {
        String tableName = "flashing";

        // Get Switch states
        boolean flash = flashSwitch.isChecked();
        boolean warningFlash = warningFlashSwitch.isChecked();
        boolean urgentFlash = urgentFlashSwitch.isChecked();
        boolean criticalFlash = criticalFlashSwitch.isChecked();

        // Create Flashing Feature
        Flashing flashing = new Flashing(flash, warningFlash, urgentFlash, criticalFlash);

        PreferenceUpdateRequest<Object> preferenceUpdateRequest = new PreferenceUpdateRequest<>(
                tableName,
                flashing
        );

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
            editor.putBoolean("flash", flashSwitch.isChecked());
            editor.putBoolean("warning_flash", warningFlashSwitch.isChecked());
            editor.putBoolean("urgent_flash", urgentFlashSwitch.isChecked());
            editor.putBoolean("critical_flash", criticalFlashSwitch.isChecked());
            editor.apply();

            runOnUiThread(() -> SavedText.setVisibility(View.VISIBLE));
        }).start();
    }

    private void updatePreferences(String jwtToken, TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<PreferenceUpdateResponse> flashingCall = apiClient.updateNotificationOn("Bearer " + jwtToken, preferenceUpdateRequest);

        flashingCall.enqueue(new Callback<PreferenceUpdateResponse>() {
            @Override
            public void onResponse(Call<PreferenceUpdateResponse> call, Response<PreferenceUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PreferenceUpdateResponse serverResponse = response.body();
                    Log.d(TAG, "Server Response for Flashing Update: " + serverResponse);
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
            Intent intent = new Intent(FlashingActivity.this, LoginActivity.class);
            startActivity(intent);
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
                    Log.d(TAG, "Inside refreshJWT, calling updatePreferences");
                    updatePreferences(newAccessToken, tokenManager, preferenceUpdateRequest);
                } else {
                    Log.d(TAG, "Failed to refresh JWT token: " + response.code());
                    Intent intent = new Intent(FlashingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error refreshing JWT Token", t);
                Intent intent = new Intent(FlashingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}