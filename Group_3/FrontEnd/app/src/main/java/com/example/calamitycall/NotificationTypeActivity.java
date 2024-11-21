package com.example.calamitycall;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class NotificationTypeActivity extends AppCompatActivity {

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
        SavedText.setVisibility(View.VISIBLE);
    }
}