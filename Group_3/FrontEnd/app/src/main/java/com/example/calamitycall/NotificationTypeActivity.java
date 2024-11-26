package com.example.calamitycall;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;
    TextView SavedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_type_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

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
        // Load switch states from SharedPreferences
        String watch_result = sharedPreferences.getString("watch_notification_type", "Push");
        if (watch_result.equals("Push"))
        {
            watchGroup.check(R.id.watch_push);
        }
        else
        {
            watchGroup.check(R.id.watch_popup);
        }

        String warning_result = sharedPreferences.getString("warning_notification_type", "Push");
        if (warning_result.equals("Push"))
        {
            warningGroup.check(R.id.warning_push);
        }
        else
        {
            warningGroup.check(R.id.warning_popup);
        }

        String urgent_result = sharedPreferences.getString("urgent_notification_type", "Push");
        if (urgent_result.equals("Push"))
        {
            urgentGroup.check(R.id.urgent_push);
        }
        else
        {
            urgentGroup.check(R.id.urgent_popup);
        }

        String critical_result = sharedPreferences.getString("critical_notification_type", "Push");
        if (critical_result.equals("Push"))
        {
            criticalGroup.check(R.id.critical_push);
        }
        else
        {
            criticalGroup.check(R.id.critical_popup);
        }
    }


    private void setRadioGroupListener(RadioGroup radioGroup, final String notificationType) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = findViewById(checkedId);
            String selectedText = selectedButton.getText().toString().toLowerCase();

            switch (notificationType) {
                case "Watch":
                    sharedPreferences.edit().putString("watch_notification_type", selectedText).apply();
                    break;
                case "Warning":
                    sharedPreferences.edit().putString("warning_notification_type", selectedText).apply();
                    break;
                case "Urgent":
                    sharedPreferences.edit().putString("urgent_notification_type", selectedText).apply();
                    break;
                case "Critical":
                    sharedPreferences.edit().putString("critical_notification_type", selectedText).apply();
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

        // Save switch states to SharedPreferences
        new Thread(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("watch_notification_type", watch);
            editor.putString("warning_notification_type", warning);
            editor.putString("urgent_notification_type", urgent);
            editor.putString("critical_notification_type", critical);
            editor.apply();

            // update the UI on the main thread
            runOnUiThread(() -> SavedText.setVisibility(View.VISIBLE));
        }).start();
    }


    private String getSelectedOption(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedButton = findViewById(selectedId);
            String selectedText = selectedButton.getText().toString();
            if (selectedText.equals("Pop-up")) {
                return "Popup"; // Transform "pop-up" to "popup"
            }
            else {
                return selectedText; // Return other values as they are
            }
        }
        {
            return "Popup"; // Default to "popup" if nothing is selected
        }
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