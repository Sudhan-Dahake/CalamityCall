package com.example.calamitycall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class NotificationOnActivity extends AppCompatActivity {

    private Switch watchSwitch;
    private Switch warningSwitch;
    private Switch urgentSwitch;
    private Switch criticalSwitch;
    private TextView settings;
    private Button saveButton; // Save button

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_on_page);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        // Find UI elements
        watchSwitch = findViewById(R.id.watch_switch);
        warningSwitch = findViewById(R.id.warning_switch);
        urgentSwitch = findViewById(R.id.urgent_switch);
        criticalSwitch = findViewById(R.id.critical_switch);
        settings = findViewById(R.id.settings_title);
        saveButton = findViewById(R.id.notificationon_save);


        // Load saved preferences
        loadPreferences();

        // Set up the settings button listener
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start fragment transaction to open settings page
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SettingsPage());
                transaction.commit();
                finish(); // Close current activity
            }
        });

        // Set up save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    private void loadPreferences() {
        // Load switch states from SharedPreferences
        watchSwitch.setChecked(sharedPreferences.getBoolean("watch_notification", true));
        warningSwitch.setChecked(sharedPreferences.getBoolean("warning_notification", true));
        urgentSwitch.setChecked(sharedPreferences.getBoolean("urgent_notification", true));
        criticalSwitch.setChecked(sharedPreferences.getBoolean("critical_notification", true));
    }

    private void savePreferences() {
        // Save switch states to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("watch_notification", watchSwitch.isChecked());
        editor.putBoolean("warning_notification", warningSwitch.isChecked());
        editor.putBoolean("urgent_notification", urgentSwitch.isChecked());
        editor.putBoolean("critical_notification", criticalSwitch.isChecked());
        editor.apply();
    }
}
