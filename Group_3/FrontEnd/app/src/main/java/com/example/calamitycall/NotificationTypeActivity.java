package com.example.calamitycall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;
import com.example.calamitycall.models.preference.NotificationAlertType;
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

public class NotificationTypeActivity extends AppCompatActivity {
    private static final String TAG = "NotificationTypeActivity";

    private RadioGroup watchGroup, warningGroup, urgentGroup, criticalGroup;
    private SettingsPreferences settingsPreferences;
    TextView SavedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_type_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        settingsPreferences = new SettingsPreferences(this);

        watchGroup = findViewById(R.id.watch_Group);
        warningGroup = findViewById(R.id.warning_Group);
        urgentGroup = findViewById(R.id.urgent_Group);
        criticalGroup = findViewById(R.id.critical_Group);

        Button saveButton = findViewById(R.id.notificationtype_save);
        SavedText = findViewById(R.id.SavedText);

        TextView settings = findViewById(R.id.settings_title);

        loadPreferences();

        // Set up save button listener
        saveButton.setOnClickListener(v -> savePreferences());

        setRadioGroupListener(watchGroup, "Watch");
        setRadioGroupListener(warningGroup, "Warning");
        setRadioGroupListener(urgentGroup, "Urgent");
        setRadioGroupListener(criticalGroup, "Critical");

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
        setDefaultOrSavedSelection(watchGroup, settingsPreferences.getWatchNotificationType());
        setDefaultOrSavedSelection(warningGroup, settingsPreferences.getWarningNotificationType());
        setDefaultOrSavedSelection(urgentGroup, settingsPreferences.getUrgentNotificationType());
        setDefaultOrSavedSelection(criticalGroup, settingsPreferences.getCriticalNotificationType());
    }

    private void setDefaultOrSavedSelection(RadioGroup radioGroup, String notificationType) {
        int radioButtonId;

        // If there is no saved preference, default to the "Pop-up" option for each notification type
        if (notificationType == null || notificationType.isEmpty()) {
            // Default to Pop-up
            radioButtonId = getPopupRadioButtonId(radioGroup);
        } else {
            // Set the selected radio button based on the saved setting
            switch (notificationType.toLowerCase()) {
                case "push":
                    radioButtonId = getPushRadioButtonId(radioGroup);
                    break;
                case "popup":
                    radioButtonId = getPopupRadioButtonId(radioGroup);
                    break;
                default:
                    // If the type is unrecognized, default to Pop-up
                    radioButtonId = getPopupRadioButtonId(radioGroup);
                    break;
            }
        }

        // Check the determined radio button ID
        if (radioButtonId != -1) {
            radioGroup.check(radioButtonId);
        }
    }

    private int getPushRadioButtonId(RadioGroup radioGroup) {
        int id = radioGroup.getId();
        if (id == R.id.watch_Group) {
            return R.id.watch_push;
        } else if (id == R.id.warning_Group) {
            return R.id.warning_push;
        } else if (id == R.id.urgent_Group) {
            return R.id.urgent_push;
        } else if (id == R.id.critical_Group) {
            return R.id.critical_push;
        }
        return -1; // If no match, return -1
    }

    private int getPopupRadioButtonId(RadioGroup radioGroup) {
        int id = radioGroup.getId();
        if (id == R.id.watch_Group) {
            return R.id.watch_popup;
        } else if (id == R.id.warning_Group) {
            return R.id.warning_popup;
        } else if (id == R.id.urgent_Group) {
            return R.id.urgent_popup;
        } else if (id == R.id.critical_Group) {
            return R.id.critical_popup;
        }
        return -1; // If no match, return -1
    }

    private void setRadioGroupListener(RadioGroup radioGroup, final String notificationType) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = findViewById(checkedId);
            String selectedText = selectedButton.getText().toString();

            switch (notificationType) {
                case "Watch":
                    settingsPreferences.setWatchNotificationType(selectedText.toLowerCase());
                    break;
                case "Warning":
                    settingsPreferences.setWarningNotificationType(selectedText.toLowerCase());
                    break;
                case "Urgent":
                    settingsPreferences.setUrgentNotificationType(selectedText.toLowerCase());
                    break;
                case "Critical":
                    settingsPreferences.setCriticalNotificationType(selectedText.toLowerCase());
                    break;
            }
            SavedText.setVisibility(View.INVISIBLE);

            Toast.makeText(NotificationTypeActivity.this,
                    notificationType + " notification set to: " + selectedText, Toast.LENGTH_SHORT).show();
        });
    }

    private void savePreferences() {
        String tableName = "notification_alert_type";

        // Get selected values from RadioGroups
        String watch = getSelectedOption(watchGroup);
        String warning = getSelectedOption(warningGroup);
        String urgent = getSelectedOption(urgentGroup);
        String critical = getSelectedOption(criticalGroup);

        // Create NotificationType object
        NotificationAlertType notificationType = new NotificationAlertType(watch, warning, urgent, critical);

        // Create PreferenceUpdateRequest
        PreferenceUpdateRequest<Object> preferenceUpdateRequest = new PreferenceUpdateRequest<>(
                tableName,
                notificationType
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


    private String getSelectedOption(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedButton = findViewById(selectedId);
            String selectedText = selectedButton.getText().toString();
            if (selectedText.equals("Pop-up")) {
                return "Popup"; // Transform "pop-up" to "popup"
            }
            return selectedText; // Return other values as they are
        }
        return "Popup"; // Default to "popup" if nothing is selected
    }


    private void updatePreferences(String jwtToken, TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<PreferenceUpdateResponse> notificationTypeCall = apiClient.updatePreference("Bearer " + jwtToken, preferenceUpdateRequest);

        notificationTypeCall.enqueue(new Callback<PreferenceUpdateResponse>() {
            @Override
            public void onResponse(Call<PreferenceUpdateResponse> call, Response<PreferenceUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("NotificationType", "Server Response: " + response.body());
                    SavedText.setVisibility(View.VISIBLE);
                    SavedText.setText("Preferences Saved Successfully!");
                } else {
                    SavedText.setVisibility(View.VISIBLE);
                    SavedText.setText("Failed to Save Preferences!!");
                }
            }

            @Override
            public void onFailure(Call<PreferenceUpdateResponse> call, Throwable t) {
                Log.e("NotificationType", "Error saving preferences", t);
                SavedText.setVisibility(View.VISIBLE);
                SavedText.setText("Error Saving Preferences!!");
            }
        });
    }


    private void refreshJWT(TokenManager tokenManager, PreferenceUpdateRequest<Object> preferenceUpdateRequest) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            Log.d(TAG, "No refresh token available");
            startActivity(new Intent(NotificationTypeActivity.this, LoginActivity.class));
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
                    startActivity(new Intent(NotificationTypeActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Error refreshing JWT Token", t);
                startActivity(new Intent(NotificationTypeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}