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
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class FlashingActivity extends AppCompatActivity {

    private Switch flashSwitch;
    private Switch warningFlashSwitch;
    private Switch urgentFlashSwitch;
    private Switch criticalFlashSwitch;
    private TextView settings;
    private Button saveButton;  // New save button

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_flashing_page);

        sharedPreferences = getSharedPreferences("FlashingPreferences", MODE_PRIVATE);

        flashSwitch = findViewById(R.id.watch_switch);
        warningFlashSwitch = findViewById(R.id.warning_switch);
        urgentFlashSwitch = findViewById(R.id.urgent_switch);
        criticalFlashSwitch = findViewById(R.id.critical_switch);
        settings = findViewById(R.id.settings_title);
        saveButton = findViewById(R.id.flashing_save);  // Initialize save button

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
        flashSwitch.setChecked(sharedPreferences.getBoolean("flash", false));
        warningFlashSwitch.setChecked(sharedPreferences.getBoolean("warning_flash", false));
        urgentFlashSwitch.setChecked(sharedPreferences.getBoolean("urgent_flash", false));
        criticalFlashSwitch.setChecked(sharedPreferences.getBoolean("critical_flash", false));
    }


    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("watch_flash", flashSwitch.isChecked());
        editor.putBoolean("warning_flash", warningFlashSwitch.isChecked());
        editor.putBoolean("urgent_flash", urgentFlashSwitch.isChecked());
        editor.putBoolean("critical_flash", criticalFlashSwitch.isChecked());
        editor.apply();
    }

}


