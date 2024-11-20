package com.example.calamitycall.network.auth;

import android.content.Context;
import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginService {
    private ApiClient apiClient;
    private TokenManager tokenManager;

    public LoginService(Context context) {
        this.apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);

        try {
            this.tokenManager = TokenManager.getInstance(context);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();

            // Here we will put whatever we wanna show to the user.
        }
    }

    // Constructor for testing (allows mock ApiClient injection)
    public LoginService(ApiClient apiClient, TokenManager tokenManager) {
        this.apiClient = apiClient;
        this.tokenManager = tokenManager;
    }

    public void login(LoginRequest loginRequest, final LoginCallback callback) {
        Call<LoginResponse> call = this.apiClient.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Saving tokens using TokenManager
                    tokenManager.setAccessToken(loginResponse.getAccessToken());
                    tokenManager.setRefreshToken(loginResponse.getRefreshToken());

                    callback.onSuccess(loginResponse);
                } else {
                    callback.onError(new Throwable("Login failed with code; " + response.code()));
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    public interface LoginCallback {
        void onSuccess(LoginResponse loginResponse);
        void onError(Throwable throwable);
    }
}
