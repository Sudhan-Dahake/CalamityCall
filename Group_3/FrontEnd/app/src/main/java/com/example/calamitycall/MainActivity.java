package com.example.calamitycall;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calamitycall.fragments.AccountPage;
import com.example.calamitycall.fragments.BoardsPage;
import com.example.calamitycall.fragments.HistoryPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_boards);
        bottomNav.setOnItemSelectedListener(navListener);



        Fragment selectedFragment = new BoardsPage();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
        int itemId = item.getItemId();
                Fragment selectedFragment = null;

                if (itemId == R.id.nav_boards) {
                    selectedFragment = new BoardsPage();
                } else if (itemId == R.id.nav_history) {
                    selectedFragment = new HistoryPage();
                } else if (itemId == R.id.nav_account) {
                    selectedFragment = new AccountPage();

                } else {
                    selectedFragment = new BoardsPage();
                }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}
