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
        setContentView(R.layout.activity_settings_notification_on_page); //load the notification on page

        settingsPreferences = new SettingsPreferences(this); //load the settings preferences

        watchSwitch = findViewById(R.id.watch_switch); //find the watch switch
        warningSwitch = findViewById(R.id.warning_switch); //find the warning switch
        urgentSwitch = findViewById(R.id.urgent_switch); //find the urgent switch
        criticalSwitch = findViewById(R.id.critical_switch); //find the critical switch

        settings = findViewById(R.id.settings_title); //find the settings button

        loadPreferences();

        watchSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //setOnCheckedChangeListener for the watch switch
        warningSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //setOnCheckedChangeListener for the warning switch
        urgentSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //setOnCheckedChangeListener for the urgent switch
        criticalSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //setOnCheckedChangeListener for the critical switch

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationOnActivity.this, Settings.class); //change to settings layout when the settings button pushed
                startActivity(intent);
            }
        });

    }

    private void loadPreferences() {
        watchSwitch.setChecked(settingsPreferences.isWatchNotificationOn()); //set the watch to saved preferences
        warningSwitch.setChecked(settingsPreferences.isWarningNotificationOn()); //set the warning to saved preferences
        urgentSwitch.setChecked(settingsPreferences.isUrgentNotificationOn()); //set the urgent to saved preferences
        criticalSwitch.setChecked(settingsPreferences.isCriticalNotificationOn()); //set the critical to saved preferences
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.watch_switch) {
            settingsPreferences.setWatchNotificationOn(isChecked); //switch the watch settings
        } else if (buttonView.getId() == R.id.warning_switch) {
            settingsPreferences.setWarningNotificationOn(isChecked); //switch the warning settings
        } else if (buttonView.getId() == R.id.urgent_switch) {
            settingsPreferences.setUrgentNotificationOn(isChecked); //switch the urgent settings
        } else if (buttonView.getId() == R.id.critical_switch) {
            settingsPreferences.setCriticalNotificationOn(isChecked); //switch the critical settings
        }

    }




}
