package com.example.calamitycall.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private static Retrofit retrofit;

    private static final String BASE_URL = "https://syncpoint-api.onrender.com";        // API URL

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Configuring OkHttpClient with a custom timeout.
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)        // Connection Timeout
                    .readTimeout(2, TimeUnit.MINUTES)           // Read Timeout
                    .writeTimeout(2, TimeUnit.MINUTES)          // Write Timeout
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit;
    }
}
