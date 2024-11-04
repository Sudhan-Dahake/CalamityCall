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
        setContentView(R.layout.activity_settings_notification_type_page);

        settingsPreferences = new SettingsPreferences(this);

        watchGroup = findViewById(R.id.watchGroup);
        warningGroup = findViewById(R.id.warningGroup);
        urgentGroup = findViewById(R.id.urgentGroup);
        criticalGroup = findViewById(R.id.criticalGroup);

        settings = findViewById(R.id.settings_title);

        loadPreferences();

        setRadioGroupListener(watchGroup, "Watch");
        setRadioGroupListener(warningGroup, "Warning");
        setRadioGroupListener(urgentGroup, "Urgent");
        setRadioGroupListener(criticalGroup, "Critical");

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationTypeActivity.this, Settings.class);
                startActivity(intent);
            }
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
        switch (notificationType) {
            case "Watch":
                radioButtonId = R.id.watch_popup;
                break;
            case "Warning":
                radioButtonId = R.id.warning_popup;
                break;
            case "Urgent":
                radioButtonId = R.id.urgent_popup;;
                break;
            case "Critical":
                radioButtonId = R.id.urgent_popup;
                break;
            default:
                radioButtonId = R.id.critical_push;
                break;
        }
        radioGroup.check(radioButtonId);
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
