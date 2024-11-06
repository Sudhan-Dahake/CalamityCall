package com.example.calamitycall.utils;

import android.content.Context;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenManager {
    private static TokenManager instance;
    private String accessToken;     // In memory storage for JWT

    private static final String PREFS_NAME = "secure_prefs";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    private final EncryptedSharedPreferences sharedPreferences;

    private TokenManager(Context context) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        this.sharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public static synchronized TokenManager getInstance(Context context) throws GeneralSecurityException, IOException {
        if (instance == null) {
            instance = new TokenManager(context.getApplicationContext());
        }

        return instance;
    }


    // Access Token in memory operations
    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void clearAccessToken() {
        this.accessToken = null;
    }


    // Refresh Token Persistent Operations
    public void setRefreshToken(String token) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, token).apply();
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    public void clearRefreshToken() {
        sharedPreferences.edit().remove(REFRESH_TOKEN_KEY).apply();
    }
}
