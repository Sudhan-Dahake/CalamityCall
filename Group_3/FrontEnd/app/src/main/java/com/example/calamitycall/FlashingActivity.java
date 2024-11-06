package com.example.calamitycall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FlashingActivity extends AppCompatActivity {

    private Switch watchflashSwitch;
    private Switch warningFlashSwitch;
    private Switch urgentFlashSwitch;
    private Switch criticalFlashSwitch;
    private TextView settings;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_flashing_page); //load the flashing layout


        sharedPreferences = getSharedPreferences("FlashingPreferences", MODE_PRIVATE); //load the flashing settings

        watchflashSwitch = findViewById(R.id.watch_switch); //find the watch switch
        warningFlashSwitch = findViewById(R.id.warning_switch); //find the warning switch
        urgentFlashSwitch = findViewById(R.id.urgent_switch); //find the urgent switch
        criticalFlashSwitch = findViewById(R.id.critical_switch); //find the critical switch

        settings = findViewById(R.id.settings_title); //finds the settings title

        loadPreferences(); //load the preferences into the layout

        watchflashSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the watch
        warningFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the watch
        urgentFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the watch
        criticalFlashSwitch.setOnCheckedChangeListener(this::onSwitchChanged); //adds a setOnCheckedChangeListener for the watch

        settings.setOnClickListener(new View.OnClickListener() { //on click listener for the Settings button at the top of the page
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashingActivity.this, Settings.class);
                startActivity(intent);
            }
        });


    }

    private void loadPreferences() {
        watchflashSwitch.setChecked(sharedPreferences.getBoolean("flash", true)); //set the watch to saved preferences
        warningFlashSwitch.setChecked(sharedPreferences.getBoolean("warning_flash", true)); //set the warning to saved preferences
        urgentFlashSwitch.setChecked(sharedPreferences.getBoolean("urgent_flash", true)); //set the urgent to saved preferences
        criticalFlashSwitch.setChecked(sharedPreferences.getBoolean("critical_flash", true)); //set the critical to saved preferences
    }

    private void onSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (buttonView.getId() == R.id.watch_switch) {
            editor.putBoolean("watch_flash", isChecked); //switch the watch settings
        } else if (buttonView.getId() == R.id.warning_switch) {
            editor.putBoolean("warning_flash", isChecked); //switch the warning settings
        } else if (buttonView.getId() == R.id.urgent_switch) {
            editor.putBoolean("urgent_flash", isChecked); //switch the urgent settings
        } else if (buttonView.getId() == R.id.critical_switch) {
            editor.putBoolean("critical_flash", isChecked); //switch the critical settings
        }

        editor.apply(); //apply the settings
    }
}
