package com.example.calamitycall.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private static Retrofit retrofit;

    private static final String BASE_URL = "https://syncpoint-api.onrender.com";        // API URL

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Added these things to the retrofit for very helpful API debugging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Set log level

            // Configuring OkHttpClient with a custom timeout.
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)        // Connection Timeout
                    .readTimeout(2, TimeUnit.MINUTES)           // Read Timeout
                    .writeTimeout(2, TimeUnit.MINUTES)          // Write Timeout
                    .addInterceptor(loggingInterceptor) // Add the logging interceptor
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit;
    }
}
