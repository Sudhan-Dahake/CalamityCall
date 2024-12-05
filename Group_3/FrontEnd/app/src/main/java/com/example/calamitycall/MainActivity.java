package com.example.calamitycall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.session.MediaSession;
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
import com.example.calamitycall.fragments.PlaceholderFragment;
import com.example.calamitycall.fragments.ReportPage;
import com.example.calamitycall.fragments.NotificationPage;
import com.example.calamitycall.fragments.SettingsPage;
import com.example.calamitycall.models.preference.PreferenceResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.models.token.VerifyTokenResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenCallback;
import com.example.calamitycall.utils.TokenManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.calamitycall.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityResultLauncher<Intent> loginActivityLauncher;

    private SettingsPreferences settingsPreferences;

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
                        loadSettingsPage(false);
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


    private void loadSettingsPage(boolean requireLoginPrompt) {
        if (requireLoginPrompt) {
            Log.d("MainActivity", "No Login required to load settings");
            // Directly load the SettingsPage fragment without prompting login
            Fragment settingsFragment = new SettingsPage();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, settingsFragment)
                    .commit();
            return;
        }


        // Show the placeholder fragment in the fragment container
        Fragment placeholderFragment = new PlaceholderFragment(); // Create a new fragment for the loading page
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, placeholderFragment)
                .commit();

        // Perform token handling
        tokenStuff(() -> {
            // Ensure a short delay to let the placeholder fragment render
            runOnUiThread(() -> {
                // Replace the placeholder with the SettingsPage fragment
                Fragment settingsFragment = new SettingsPage();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, settingsFragment)
                        .commit();
            });
        });
    }



    private void tokenStuff(TokenCallback callback) {
        TokenManager tokenManager;

        try {
            tokenManager = TokenManager.getInstance(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "TokenManager Initialization Failed", e);
            callback.onComplete(); // Notify completion if initialization fails
            return;
        }

        String jwtToken = tokenManager.getAccessToken();

        if (jwtToken != null) {
            fetchPreferences(jwtToken, tokenManager, callback);
        } else {
            refreshJWT(tokenManager, callback);
        }
    }

    private void refreshJWT(TokenManager tokenManager, TokenCallback callback) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            Log.d(TAG, "No refresh token available");
            callback.onComplete(); // Notify completion if refresh token is unavailable
            return;
        }

        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
        Call<TokenResponse> refreshCall = apiClient.refreshToken(new TokenGenerateRequest(refreshToken));
        refreshCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newAccessToken = response.body().getAccessToken();
                    tokenManager.setAccessToken(newAccessToken);
                    fetchPreferences(newAccessToken, tokenManager, callback);
                } else {
                    Log.d(TAG, "Failed to refresh JWT token: " + response.code());
                    callback.onComplete(); // Notify completion
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Error refreshing JWT Token", t);
                callback.onComplete(); // Notify completion
            }
        });
    }

    private void fetchPreferences(String jwtToken, TokenManager tokenManager, TokenCallback callback) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
        Call<PreferenceResponse> call = apiClient.getPreferences("Bearer " + jwtToken);
        call.enqueue(new Callback<PreferenceResponse>() {
            @Override
            public void onResponse(Call<PreferenceResponse> call, Response<PreferenceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PreferenceResponse preferenceResponse = response.body();
                    SettingsPreferences settingsPreferences = new SettingsPreferences(getApplicationContext());

                    // Store preferences in memory
                    settingsPreferences.setWatchNotificationOn(preferenceResponse.getNotificationOn().getWatch());
                    settingsPreferences.setWarningNotificationOn(preferenceResponse.getNotificationOn().getWarning());
                    settingsPreferences.setUrgentNotificationOn(preferenceResponse.getNotificationOn().getUrgent());
                    settingsPreferences.setCriticalNotificationOn(preferenceResponse.getNotificationOn().getCritical());

                    settingsPreferences.setWatchFlashingOn(preferenceResponse.getFlashing().getWatch());
                    settingsPreferences.setWarningFlashingOn(preferenceResponse.getFlashing().getWarning());
                    settingsPreferences.setUrgentFlashingOn(preferenceResponse.getFlashing().getUrgent());
                    settingsPreferences.setCriticalFlashingOn(preferenceResponse.getFlashing().getCritical());

                    settingsPreferences.setWatchNoiseOn(preferenceResponse.getNoise().getWatch());
                    settingsPreferences.setWarningNoiseOn(preferenceResponse.getNoise().getWarning());
                    settingsPreferences.setUrgentNoiseOn(preferenceResponse.getNoise().getUrgent());
                    settingsPreferences.setCriticalNoiseOn(preferenceResponse.getNoise().getCritical());

                    settingsPreferences.setWatchNotificationType(preferenceResponse.getNotificationAlertType().getWatch());
                    settingsPreferences.setWarningNotificationType(preferenceResponse.getNotificationAlertType().getWarning());
                    settingsPreferences.setUrgentNotificationType(preferenceResponse.getNotificationAlertType().getUrgent());
                    settingsPreferences.setCriticalNotificationType(preferenceResponse.getNotificationAlertType().getCritical());

                    settingsPreferences.setWatchTTSOn(preferenceResponse.getTextToSpeech().getWatch());
                    settingsPreferences.setWarningTTSOn(preferenceResponse.getTextToSpeech().getWarning());
                    settingsPreferences.setUrgentTTSOn(preferenceResponse.getTextToSpeech().getUrgent());
                    settingsPreferences.setCriticalTTSOn(preferenceResponse.getTextToSpeech().getCritical());

                    Log.d(TAG, "Preferences Loaded Successfully");
                } else if (response.code() == 401) {
                    refreshJWT(tokenManager, callback);
                }
                callback.onComplete(); // Notify completion
            }

            @Override
            public void onFailure(Call<PreferenceResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching Preferences", t);
                callback.onComplete(); // Notify completion
            }
        });
    }


    private void launchLoginActivity() {
        // Launch the LoginActivity and wait for the result.
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginActivityLauncher.launch(loginIntent);
    }


    private void refreshToken(TokenManager tokenManager) {
        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null) {
            Log.d(TAG, "No refresh token available, redirecting to LoginActivity.");

            launchLoginActivity();

            return;
        }

        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<TokenResponse> refreshCall = apiClient.refreshToken(new TokenGenerateRequest(refreshToken));
        refreshCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newAccessToken = response.body().getAccessToken();

                    tokenManager.setAccessToken(newAccessToken);

                    Log.d(TAG, "JWT refreshed successfully, loading Settings Page");

                    loadSettingsPage(true);
                }

                else {
                    Log.e(TAG, "Failed to refresh JWT, redirecting to LoginActivity");

                    launchLoginActivity();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e(TAG, "Error refreshing JWT", t);

                launchLoginActivity();
            }
        });
    }

    private void verifyToken(String jwtToken, TokenManager tokenManager) {
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<VerifyTokenResponse> call = apiClient.verifyToken("Bearer " + jwtToken);
        call.enqueue(new Callback<VerifyTokenResponse>() {
            @Override
            public void onResponse(Call<VerifyTokenResponse> call, Response<VerifyTokenResponse> response) {
                if (response.isSuccessful()) {
                    // JWT is valid, load the settings page.
                    Log.d(TAG, "JWT is valid, loading Settings Page");

                    loadSettingsPage(true);
                }

                else if (response.code() == 401) {
                    Log.d(TAG, "JWT is invalid, attempting to refresh");

                    refreshToken(tokenManager);
                }
            }

            @Override
            public void onFailure(Call<VerifyTokenResponse> call, Throwable t) {
                Log.e(TAG, "Error verifying JWT", t);

                launchLoginActivity();
            }
        });
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
                    try {
                        TokenManager tokenManager = TokenManager.getInstance(getApplicationContext());

                        String jwtToken = tokenManager.getAccessToken();

                        if (jwtToken != null) {
                            verifyToken(jwtToken, tokenManager);
                        }

                        else {
                            refreshToken(tokenManager);
                        }
                    }

                    catch (Exception e) {
                        Log.e(TAG, "Error initializing TokenManager", e);

                        launchLoginActivity();
                    }


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