package com.example.calamitycall;

import android.app.Application;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();

                        // sending token to the backend here (yet to be implemented)
                    }
                });
    }
}
