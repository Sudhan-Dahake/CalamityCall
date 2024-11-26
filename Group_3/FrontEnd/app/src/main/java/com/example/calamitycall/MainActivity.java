package com.example.calamitycall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.calamitycall.fragments.ForumPage;
import com.example.calamitycall.fragments.ReportPage;
import com.example.calamitycall.fragments.NotificationPage;
import com.example.calamitycall.fragments.SettingsPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setStatusBarColor(Color.BLACK);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Default tab (Forum)
        Fragment selectedFragment = new ForumPage();
        bottomNav.setSelectedItemId(R.id.nav_forum);

        // Handle intent extras for selecting a tab
        String openTab = getIntent().getStringExtra("open_tab");
        if ("notifications".equals(openTab)) {
            selectedFragment = new NotificationPage(); // Switch to Notifications tab
            bottomNav.setSelectedItemId(R.id.nav_notification);
        }

        // Set the selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        bottomNav.setOnItemSelectedListener(navListener);
    }

    // Handle new intents if the app is already running
    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);

        String openTab = intent.getStringExtra("open_tab");
        if ("notifications".equals(openTab)) {
            Fragment selectedFragment = new NotificationPage();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.nav_notification);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                int itemId = item.getItemId();
                Fragment selectedFragment;

                if (itemId == R.id.nav_forum) {
                    selectedFragment = new ForumPage();
                } else if (itemId == R.id.nav_report) {
                    selectedFragment = new ReportPage();
                } else if (itemId == R.id.nav_notification) {
                    selectedFragment = new NotificationPage();
                } else if (itemId == R.id.nav_settings) {
                    selectedFragment = new SettingsPage();
                } else {
                    selectedFragment = new ForumPage();
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            };
}