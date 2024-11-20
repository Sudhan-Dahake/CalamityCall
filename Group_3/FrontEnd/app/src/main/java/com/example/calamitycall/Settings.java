package com.example.calamitycall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    private Button notificationOnButton;
    private Button notificationAlertTypeButton;
    private Button flashingButton;
    private Button noiseButton;
    private Button texttospeechButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        notificationOnButton = findViewById(R.id.notification_on);
        notificationAlertTypeButton = findViewById(R.id.notification_alert_type);
        flashingButton = findViewById(R.id.flashing);
        noiseButton = findViewById(R.id.noise);
        texttospeechButton = findViewById(R.id.text_to_speech);

        notificationOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, NotificationOnActivity.class);
                startActivity(intent);
            }
        });

        notificationAlertTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        texttospeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Text to speech activity
                Intent intent = new Intent(Settings.this, TexttospeechActivity.class);
                startActivity(intent);
            }
        });
    }
}
