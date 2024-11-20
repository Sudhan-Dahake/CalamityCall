package com.example.calamitycall;

import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.Color;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class TexttospeechActivity extends AppCompatActivity {

    private Switch watchTTSSwitch; // Main switch for watch noise notifications
    private Switch warningTTSSwitch;
    private Switch urgentTTSSwitch;
    private Switch criticalTTSSwitch;
    private TextView settings;

    private Button saveButton;  // New save button
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_texttospeech_page);

        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);



        watchTTSSwitch = findViewById(R.id.watch_switch);
        warningTTSSwitch = findViewById(R.id.warning_switch);
        urgentTTSSwitch = findViewById(R.id.urgent_switch);
        criticalTTSSwitch = findViewById(R.id.critical_switch);
        saveButton = findViewById(R.id.text_to_speech_save);
        settings = findViewById(R.id.settings_title);

        loadPreferences();

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

        saveButton.setOnClickListener(new View.OnClickListener() {  // Set listener for save button
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    private void loadPreferences() {
        watchTTSSwitch.setChecked(sharedPreferences.getBoolean("tts_watch", false));
        warningTTSSwitch.setChecked(sharedPreferences.getBoolean("tts_warning", false));
        urgentTTSSwitch.setChecked(sharedPreferences.getBoolean("tts_urgent", false));
        criticalTTSSwitch.setChecked(sharedPreferences.getBoolean("tts_critical", false));
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("tts_watch", watchTTSSwitch.isChecked());
        editor.putBoolean("tts_warning", warningTTSSwitch.isChecked());
        editor.putBoolean("tts_urgent", urgentTTSSwitch.isChecked());
        editor.putBoolean("tts_critical", criticalTTSSwitch.isChecked());
        editor.apply();
    }
}
