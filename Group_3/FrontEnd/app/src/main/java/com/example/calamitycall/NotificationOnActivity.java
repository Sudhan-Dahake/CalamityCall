package com.example.calamitycall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationOnActivity extends AppCompatActivity {

    private Switch watchSwitch;
    private Switch warningSwitch;
    private Switch urgentSwitch;
    private Switch criticalSwitch;
    private TextView settings;
    private SettingsPreferences settingsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_on_page);

        settingsPreferences = new SettingsPreferences(this);

        watchSwitch = findViewById(R.id.watch_switch);
        warningSwitch = findViewById(R.id.warning_switch);
        urgentSwitch = findViewById(R.id.urgent_switch);
        criticalSwitch = findViewById(R.id.critical_switch);

        settings = findViewById(R.id.settings_title);

        loadPreferences();

        watchSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationOnActivity.this, Settings.class);
                startActivity(intent);
            }
        });

    }

    private void loadPreferences() {
        watchSwitch.setChecked(settingsPreferences.isWatchNotificationOn());
        warningSwitch.setChecked(settingsPreferences.isWarningNotificationOn());
        urgentSwitch.setChecked(settingsPreferences.isUrgentNotificationOn());
        criticalSwitch.setChecked(settingsPreferences.isCriticalNotificationOn());
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.watch_switch) {
            settingsPreferences.setWatchNotificationOn(isChecked);
        } else if (buttonView.getId() == R.id.warning_switch) {
            settingsPreferences.setWarningNotificationOn(isChecked);
        } else if (buttonView.getId() == R.id.urgent_switch) {
            settingsPreferences.setUrgentNotificationOn(isChecked);
        } else if (buttonView.getId() == R.id.critical_switch) {
            settingsPreferences.setCriticalNotificationOn(isChecked);
        }

    }




}
