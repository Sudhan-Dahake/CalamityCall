package com.example.calamitycall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoiseActivity extends AppCompatActivity {

    private Switch noiseSwitch;
    private Switch warningNoiseSwitch;
    private Switch urgentNoiseSwitch;
    private Switch criticalNoiseSwitch;
    private TextView settings;

    private SettingsPreferences settingsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_noise_page); //load the noise page

        settingsPreferences = new SettingsPreferences(this); //load the saved settings

        noiseSwitch = findViewById(R.id.watch_switch); //find the watch switch
        warningNoiseSwitch = findViewById(R.id.warning_switch); //find the warning switch
        urgentNoiseSwitch = findViewById(R.id.urgent_switch); //find the urgent switch
        criticalNoiseSwitch = findViewById(R.id.critical_switch); //find the critical switch

        settings = findViewById(R.id.settings_title);          //finds the settings part of the layout

        loadPreferences(); //load the saved preferences

        noiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the watch
        warningNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the warning
        urgentNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the urgent
        criticalNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the critical

        settings.setOnClickListener(new View.OnClickListener() {  //settings button takes the user back to the settings page
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoiseActivity.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    private void loadPreferences() {
        noiseSwitch.setChecked(settingsPreferences.isWatchNoiseOn());   //set the watch to saved preferences
        warningNoiseSwitch.setChecked(settingsPreferences.isWarningNoiseOn());  //set the warning to saved preferences
        urgentNoiseSwitch.setChecked(settingsPreferences.isUrgentNoiseOn());  //set the urgent to saved preferences
        criticalNoiseSwitch.setChecked(settingsPreferences.isCriticalNoiseOn());   //set the critical to saved preferences
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.watch_switch) {
            settingsPreferences.setWatchNoiseOn(isChecked); //switch the watch setting
        } else if (buttonView.getId() == R.id.warning_switch) {
            settingsPreferences.setWarningNoiseOn(isChecked);  //switch the warning setting
        } else if (buttonView.getId() == R.id.urgent_switch) {
            settingsPreferences.setUrgentNoiseOn(isChecked);   //switch the urgent setting
        } else if (buttonView.getId() == R.id.critical_switch) {
            settingsPreferences.setCriticalNoiseOn(isChecked);   //switch the critical setting
        }
    }
}
