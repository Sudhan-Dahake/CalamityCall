package com.example.calamitycall;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

        // Check and request notification permission if on Android 13 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_forum);
        bottomNav.setOnItemSelectedListener(navListener);

        Fragment selectedFragment = new ForumPage();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                int itemId = item.getItemId();
                Fragment selectedFragment = null;

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

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };
}
