package com.example.calamitycall;


import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FlashingActivity extends AppCompatActivity {

    private Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_on_page);

        // Initialize the switch
        notificationSwitch = findViewById(R.id.warning_switch);

        // Set a listener for the switch
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Notifications enabled
                    Toast.makeText(FlashingActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    // Notifications disabled
                    Toast.makeText(FlashingActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
