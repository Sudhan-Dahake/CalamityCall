package com.example.calamitycall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calamitycall.FlashingActivity;
import com.example.calamitycall.NoiseActivity;
import com.example.calamitycall.NotificationOnActivity;
import com.example.calamitycall.NotificationTypeActivity;
import com.example.calamitycall.R;
import com.example.calamitycall.TexttospeechActivity;

public class SettingsPage extends Fragment {

    private View view;
    private Button notificationOnButton;
    private Button notificationAlertTypeButton;
    private Button flashingButton;
    private Button noiseButton;
    private Button texttospeechButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_settings_page, container, false);

        // Initialize buttons
        notificationOnButton = view.findViewById(R.id.notification_on);
        notificationAlertTypeButton = view.findViewById(R.id.notification_alert_type);
        flashingButton = view.findViewById(R.id.flashing);
        noiseButton = view.findViewById(R.id.noise);
        texttospeechButton = view.findViewById(R.id.text_to_speech);

        // Set click listeners for each button
        notificationOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationOnActivity.class);
            }
        });

        notificationAlertTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationTypeActivity.class);
            }
        });

        flashingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FlashingActivity.class);
            }
        });

        noiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NoiseActivity.class);
            }
        });

        texttospeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TexttospeechActivity.class);
            }
        });

        return view;
    }

    // Helper method to open an activity safely
    private void openActivity(Class<?> activityClass) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), activityClass);
            startActivity(intent);
        }
    }
}
