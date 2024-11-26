package com.example.calamitycall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.calamitycall.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityResultLauncher<Intent> loginActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register the ActivityResultLauncher for LoginActivity
        loginActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d("MainActivity", "Login Successful but activityPage opened");
                        // Login was successful, load the settingsPage fragment
                        Fragment settingsFragment = new SettingsPage();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, settingsFragment)
                                .commit();
                    }

                    else {
                        // Login failed or was cancelled
                        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
                        bottomNav.setSelectedItemId(R.id.nav_notification);         // Set to NotificationPage by default
                        Fragment notificationFragment = new NotificationPage();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, notificationFragment)
                                .commit();
                    }
                }
        );

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


        // Set NotificationPage as the default fragment
        Fragment selectedFragment = new NotificationPage();
        bottomNav.setSelectedItemId(R.id.nav_notification);

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
                    // Launch the LoginActivity and wait for the result.
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginActivityLauncher.launch(loginIntent);
                    return true; // Return true to indicate the menu item was handled

                    //selectedFragment = new SettingsPage();
                } else {
                    selectedFragment = new ForumPage();
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            };
}