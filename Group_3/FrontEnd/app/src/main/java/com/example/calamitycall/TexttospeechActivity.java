package com.example.calamitycall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class TexttospeechActivity extends AppCompatActivity {

    private Switch watchSwitch; // Main switch for watch noise notifications
    private Switch warningSwitch;
    private Switch urgentSwitch;
    private Switch criticalSwitch;
    private TextView settings;

    private SettingsPreferences settingsPreferences;

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

        settings = findViewById(R.id.settings_title);

        loadPreferences();

        watchSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

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
        watchSwitch.setChecked(settingsPreferences.isWatchNoiseOn());
        warningSwitch.setChecked(settingsPreferences.isWarningNoiseOn());
        urgentSwitch.setChecked(settingsPreferences.isUrgentNoiseOn());
        criticalSwitch.setChecked(settingsPreferences.isCriticalNoiseOn());
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
