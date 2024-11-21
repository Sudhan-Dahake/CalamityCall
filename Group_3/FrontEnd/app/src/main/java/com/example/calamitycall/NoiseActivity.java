package com.example.calamitycall;

import android.annotation.SuppressLint;
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

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class NoiseActivity extends AppCompatActivity {

    private Switch noiseSwitch; // Main switch for watch noise notifications
    private Switch warningNoiseSwitch;
    private Switch urgentNoiseSwitch;
    private Switch criticalNoiseSwitch;
    private SharedPreferences sharedPreferences;
    TextView SavedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_noise_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        // Find UI elements
        noiseSwitch = findViewById(R.id.watch_switch);
        warningNoiseSwitch = findViewById(R.id.warning_switch);
        urgentNoiseSwitch = findViewById(R.id.urgent_switch);
        criticalNoiseSwitch = findViewById(R.id.critical_switch);
        TextView settings = findViewById(R.id.settings_title);
        // Save button
        Button saveButton = findViewById(R.id.noise_save);
        SavedText = findViewById(R.id.SavedText);


        // Load saved preferences
        loadPreferences();

        noiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalNoiseSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(v -> {
            // Begin the fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace the fragment in the container with the SettingsPage fragment
            transaction.replace(R.id.fragment_container, new SettingsPage());

            // Commit the transaction
            transaction.commit();
            finish(); // This will close the FlashingActivity
        });


        saveButton.setOnClickListener(v -> savePreferences());
    }


    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
    }

    private void loadPreferences() {
        noiseSwitch.setChecked(sharedPreferences.getBoolean("watch_noise", true));
        warningNoiseSwitch.setChecked(sharedPreferences.getBoolean("warning_noise", true));
        urgentNoiseSwitch.setChecked(sharedPreferences.getBoolean("urgent_noise", true));
        criticalNoiseSwitch.setChecked(sharedPreferences.getBoolean("critical_noise", true));
    }

    private void savePreferences() {
        // Save switch states to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("watch_noise", noiseSwitch.isChecked());
        editor.putBoolean("warning_noise", warningNoiseSwitch.isChecked());
        editor.putBoolean("urgent_noise", urgentNoiseSwitch.isChecked());
        editor.putBoolean("critical_noise", criticalNoiseSwitch.isChecked());
        editor.apply();
        SavedText.setVisibility(View.VISIBLE);
    }

}