package com.example.calamitycall;

import android.app.Application;
import android.provider.Settings;
import android.util.Log;

import com.example.calamitycall.models.FirebaseToken.RegisterTokenRequest;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyApp extends Application {
    private static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        String deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d(TAG, "FCM Token: " + token);

                        // Calling the API to register the token
                        registerTokenWithServer(token, deviceid);
                    }
                    else {
                        Log.e(TAG, "Failed to get FCM token", task.getException());
                    }
                });
    }


    private void registerTokenWithServer(String token, String deviceid) {
        // Building the request object
        RegisterTokenRequest request = new RegisterTokenRequest(token, deviceid, null);

        // Get the API Client instance
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        Call<RegisterTokenResponse> call = apiClient.registerToken(request);
        call.enqueue(new Callback<RegisterTokenResponse>() {
            @Override
            public void onResponse(Call<RegisterTokenResponse> call, Response<RegisterTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterTokenResponse registerTokenResponse = response.body();

                    Log.d(TAG, "Status: " + registerTokenResponse.getStatus());
                }

                else {
                    Log.e(TAG, "Failed to register token: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<RegisterTokenResponse> call, Throwable t) {
                Log.e(TAG, "Error registering token with the server: ", t);
            }
        });
    }
}
