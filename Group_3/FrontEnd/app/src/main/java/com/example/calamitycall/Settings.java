package com.example.calamitycall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    private Button notificationOnButton;
    private Button notificationAlertTypeButton;
    private Button flashingButton;
    private Button noiseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        // Initialize buttons
        notificationOnButton = findViewById(R.id.notification_on);
        notificationAlertTypeButton = findViewById(R.id.notification_alert_type);
        flashingButton = findViewById(R.id.flashing);
        noiseButton = findViewById(R.id.noise);

        // Set onClick listeners for each button
        notificationOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NotificationOnActivity
                Intent intent = new Intent(Settings.this, NotificationOnActivity.class);
                startActivity(intent);
            }
        });

        notificationAlertTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NotificationAlertTypeActivity
                Intent intent = new Intent(Settings.this, NotificationTypeActivity.class);
                startActivity(intent);
            }
        });

        flashingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to FlashingActivity
                Intent intent = new Intent(Settings.this, FlashingActivity.class);
                startActivity(intent);
            }
        });

        noiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to NoiseActivity
                Intent intent = new Intent(Settings.this, NoiseActivity.class);
                startActivity(intent);
            }
        });
    }
}
