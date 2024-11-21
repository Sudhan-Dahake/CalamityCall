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
import com.example.calamitycall.models.preference.PreferenceUpdateRequest;
import com.example.calamitycall.models.preference.PreferenceUpdateResponse;
import com.example.calamitycall.models.preference.TextToSpeech;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TexttospeechActivity extends AppCompatActivity {
    private static final String TAG = "TexttospeechActivity";


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch watchSwitch; // Main switch for watch noise notifications
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch warningSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch urgentSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch criticalSwitch;

    private SettingsPreferences settingsPreferences;
    TextView SavedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_texttospeech_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        settingsPreferences = new SettingsPreferences(this);

        watchSwitch = findViewById(R.id.watch_switch);
        warningSwitch = findViewById(R.id.warning_switch);
        urgentSwitch = findViewById(R.id.urgent_switch);
        criticalSwitch = findViewById(R.id.critical_switch);
        Button saveButton = findViewById(R.id.text_to_speech_save);
        SavedText = findViewById(R.id.SavedText);
        TextView settings = findViewById(R.id.settings_title);

        loadPreferences();

        // Set up save button listener
        saveButton.setOnClickListener(v -> savePreferences());

        watchSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(v -> {
            // Begin the fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace the fragment in the container with the SettingsPage fragment
            transaction.replace(R.id.fragment_container, new SettingsPage());

            // Commit the transaction
            transaction.commit();
            finish(); // This will close the FlashingActivity
        });
    }

    private void loadPreferences() {
        watchSwitch.setChecked(settingsPreferences.isWatchNoiseOn());
        warningSwitch.setChecked(settingsPreferences.isWarningNoiseOn());
        urgentSwitch.setChecked(settingsPreferences.isUrgentNoiseOn());
        criticalSwitch.setChecked(settingsPreferences.isCriticalNoiseOn());
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
        if (buttonView.getId() == R.id.watch_switch) {
            settingsPreferences.setWatchNoiseOn(isChecked);
        } else if (buttonView.getId() == R.id.warning_switch) {
            settingsPreferences.setWarningNoiseOn(isChecked);
        } else if (buttonView.getId() == R.id.urgent_switch) {
            settingsPreferences.setUrgentNoiseOn(isChecked);
        } else if (buttonView.getId() == R.id.critical_switch) {
            settingsPreferences.setCriticalNoiseOn(isChecked);
        }
    }
    private void savePreferences() {
        String tableName = "text_to_speech";

        // Get Switch states
        boolean watch = watchSwitch.isChecked();
        boolean warning = warningSwitch.isChecked();
        boolean urgent = urgentSwitch.isChecked();
        boolean critical = criticalSwitch.isChecked();

        // Create TextToSpeech Feature
        TextToSpeech textToSpeech = new TextToSpeech(watch, warning, urgent, critical);

        PreferenceUpdateRequest<Object> preferenceUpdateRequest = new PreferenceUpdateRequest<>(
                tableName,
                textToSpeech
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


        SavedText.setVisibility(View.VISIBLE);
    }


    private void updatePreferences(String jwtToken, TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        // Initialize Retrofit and ApiClient
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        // Make the API Call to save preferences
        Call<PreferenceUpdateResponse> textToSpeechCall = apiClient.updatePreference("Bearer " + jwtToken, preferenceUpdateRequest);

        textToSpeechCall.enqueue(new Callback<PreferenceUpdateResponse>() {
            @Override
            public void onResponse(Call<PreferenceUpdateResponse> call, Response<PreferenceUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PreferenceUpdateResponse serverResponse = response.body();
                    Log.d("TextToSpeech", "Server Response for TextToSpeech Updation: " + serverResponse);
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

            Intent intent = new Intent(TexttospeechActivity.this, LoginActivity.class);
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

                    Log.d(TAG, "Inside refreshJWT, calling updatePreferences");

                    updatePreferences(newAccessToken, tokenManager, preferenceUpdateRequest);
                } else {
                    Log.d(TAG, "Failed to refresh JWT token: " + response.code());

                    Intent intent = new Intent(TexttospeechActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error refreshing JWT Token", t);

                Intent intent = new Intent(TexttospeechActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}