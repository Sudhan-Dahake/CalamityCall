package com.example.calamitycall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationTypeActivity extends AppCompatActivity {

    private RadioGroup watchGroup, warningGroup, urgentGroup, criticalGroup;
    private TextView settings;
    private SettingsPreferences settingsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_type_page); //load the notification type layout

        settingsPreferences = new SettingsPreferences(this); //load the settings

        watchGroup = findViewById(R.id.watch_Group); //find the watch radio group
        warningGroup = findViewById(R.id.warning_Group); //find the warning radio group
        urgentGroup = findViewById(R.id.urgent_Group); //find the urgent radio group
        criticalGroup = findViewById(R.id.critical_Group); //find the critical radio group

        settings = findViewById(R.id.settings_title); //find the settings button

        loadPreferences(); //load the settings to the page

        setRadioGroupListener(watchGroup, "Watch"); //create listener for the watch group
        setRadioGroupListener(warningGroup, "Warning"); //create listener for the warning group
        setRadioGroupListener(urgentGroup, "Urgent"); //create listener for the urgent group
        setRadioGroupListener(criticalGroup, "Critical"); //create listener for the critical group

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationTypeActivity.this, Settings.class); //change back to the settings page
                startActivity(intent);
            }
        });
    }

    private void loadPreferences() {
        setDefaultOrSavedSelection(watchGroup, settingsPreferences.getWatchNotificationType()); //set the watch to saved preferences
        setDefaultOrSavedSelection(warningGroup, settingsPreferences.getWarningNotificationType()); //set the warning to saved preferences
        setDefaultOrSavedSelection(urgentGroup, settingsPreferences.getUrgentNotificationType()); //set the urgent to saved preferences
        setDefaultOrSavedSelection(criticalGroup, settingsPreferences.getCriticalNotificationType()); //set the critical to saved preferences
    }

    private void setDefaultOrSavedSelection(RadioGroup radioGroup, String notificationType) {
        int radioButtonId;


        if (notificationType == null || notificationType.isEmpty()) {

            radioButtonId = getPopupRadioButtonId(radioGroup);
        } else {

            switch (notificationType.toLowerCase()) {
                case "push":
                    radioButtonId = getPushRadioButtonId(radioGroup);
                    break;
                case "popup":
                    radioButtonId = getPopupRadioButtonId(radioGroup);
                    break;
                default:

                    radioButtonId = getPopupRadioButtonId(radioGroup);
                    break;
            }
        }

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
        return -1;
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
        return -1;
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

            Toast.makeText(NotificationTypeActivity.this,
                    notificationType + " notification set to: " + selectedText, Toast.LENGTH_SHORT).show();
        });
    }
}
