package com.example.calamitycall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class NoiseActivity extends AppCompatActivity {

    private Switch noiseSwitch; // Main switch for watch noise notifications
    private Switch warningNoiseSwitch;
    private Switch urgentNoiseSwitch;
    private Switch criticalNoiseSwitch;
    private TextView settings;

    private SettingsPreferences settingsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_noise_page);

        settingsPreferences = new SettingsPreferences(this);

        noiseSwitch = findViewById(R.id.watch_switch);
        warningNoiseSwitch = findViewById(R.id.warning_switch);
        urgentNoiseSwitch = findViewById(R.id.urgent_switch);
        criticalNoiseSwitch = findViewById(R.id.critical_switch);

        settings = findViewById(R.id.settings_title);

        loadPreferences();

        noiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Begin the fragment transaction
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace the fragment in the container with the SettingsPage fragment
                transaction.replace(R.id.fragment_container, new SettingsPage());

                // Commit the transaction
                transaction.commit();
                finish(); // This will close the FlashingActivity
            }
        });
    }

    private void loadPreferences() {
        noiseSwitch.setChecked(settingsPreferences.isWatchNoiseOn());
        warningNoiseSwitch.setChecked(settingsPreferences.isWarningNoiseOn());
        urgentNoiseSwitch.setChecked(settingsPreferences.isUrgentNoiseOn());
        criticalNoiseSwitch.setChecked(settingsPreferences.isCriticalNoiseOn());
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
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
}
