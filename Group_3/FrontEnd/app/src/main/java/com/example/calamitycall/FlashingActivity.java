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

public class FlashingActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch flashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch warningFlashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch urgentFlashSwitch;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch criticalFlashSwitch;

    private SharedPreferences sharedPreferences;

    TextView SavedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_flashing_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        sharedPreferences = getSharedPreferences("FlashingPreferences", MODE_PRIVATE);

        flashSwitch = findViewById(R.id.watch_switch);
        warningFlashSwitch = findViewById(R.id.warning_switch);
        urgentFlashSwitch = findViewById(R.id.urgent_switch);
        criticalFlashSwitch = findViewById(R.id.critical_switch);
        TextView settings = findViewById(R.id.settings_title);
        SavedText = findViewById(R.id.SavedText);
        // New save button
        Button saveButton = findViewById(R.id.flashing_save);  // Initialize save button

        loadPreferences();

        flashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        warningFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        urgentFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);
        criticalFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged);

        settings.setOnClickListener(v -> {
            // Begin the fragment transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace the fragment in the container with the SettingsPage fragment
            transaction.replace(R.id.fragment_container, new SettingsPage());

            // Commit the transaction
            transaction.commit();
            finish(); // This will close the FlashingActivity
        });

        // Set listener for save button
        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void loadPreferences() {
        flashSwitch.setChecked(sharedPreferences.getBoolean("flash", true));
        warningFlashSwitch.setChecked(sharedPreferences.getBoolean("warning_flash", true));
        urgentFlashSwitch.setChecked(sharedPreferences.getBoolean("urgent_flash", true));
        criticalFlashSwitch.setChecked(sharedPreferences.getBoolean("critical_flash", true));
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SavedText.setVisibility(View.INVISIBLE);
    }
    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("watch_flash", flashSwitch.isChecked());
        editor.putBoolean("warning_flash", warningFlashSwitch.isChecked());
        editor.putBoolean("urgent_flash", urgentFlashSwitch.isChecked());
        editor.putBoolean("critical_flash", criticalFlashSwitch.isChecked());
        editor.apply();
        SavedText.setVisibility(View.VISIBLE);
    }

}