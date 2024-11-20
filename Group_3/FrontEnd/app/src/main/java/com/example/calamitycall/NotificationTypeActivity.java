package com.example.calamitycall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.fragments.SettingsPage;

public class NotificationTypeActivity extends AppCompatActivity {

    private RadioGroup watchGroup, warningGroup, urgentGroup, criticalGroup;
    private TextView settings;
    private SharedPreferences sharedPreferences;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_type_page);

        sharedPreferences = getSharedPreferences("NotificationPreferences", MODE_PRIVATE);

        watchGroup = findViewById(R.id.watch_Group);
        warningGroup = findViewById(R.id.warning_Group);
        urgentGroup = findViewById(R.id.urgent_Group);
        criticalGroup = findViewById(R.id.critical_Group);
        saveButton = findViewById(R.id.notificationtype_save);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });

    }

    private void loadPreferences() {
        setSelectedRadioButton(watchGroup, sharedPreferences.getString("watch_notification_type", "Pop-Up"));
        setSelectedRadioButton(warningGroup, sharedPreferences.getString("warning_notification_type", "Pop-Up"));
        setSelectedRadioButton(urgentGroup, sharedPreferences.getString("urgent_notification_type", "Pop-Up"));
        setSelectedRadioButton(criticalGroup, sharedPreferences.getString("critical_notification_type", "Pop-Up"));

    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("watch_notification_type", getSelectedRadioButtonText(watchGroup));
        editor.putString("warning_notification_type", getSelectedRadioButtonText(warningGroup));
        editor.putString("urgent_notification_type", getSelectedRadioButtonText(urgentGroup));
        editor.putString("critical_notification_type", getSelectedRadioButtonText(criticalGroup));

        editor.apply();
    }

    private void setSelectedRadioButton(RadioGroup group, String value) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (radioButton.getText().toString().equals(value)) {
                    radioButton.setChecked(true);
                    return;
                }
            }
        }
    }

    private String getSelectedRadioButtonText(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton != null ? selectedButton.getText().toString() : "Pop-Up";
    }
}