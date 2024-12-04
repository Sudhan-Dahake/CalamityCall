package com.example.calamitycall.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisasterRetrofitInstance {
    private static Retrofit Disaster_retrofit;

    private static final String BASE_URL = "https://calamitycall.onrender.com";        // API URL

    public static Retrofit getDisasterRetrofitInstance() {
        if (Disaster_retrofit == null) {
            // Added these things to the retrofit for very helpful API debugging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Set log level

            // Configuring OkHttpClient with a custom timeout.
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)        // Connection Timeout
                    .readTimeout(2, TimeUnit.MINUTES)           // Read Timeout
                    .writeTimeout(2, TimeUnit.MINUTES)          // Write Timeout
                    .addInterceptor(loggingInterceptor) // Add the logging interceptor
                    .dns(hostname -> Dns.SYSTEM.lookup(hostname))  // Use default DNS
                    .build();


            Disaster_retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return Disaster_retrofit;
    }
}

