package com.example.calamitycall.network.auth;


import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.models.signup.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SignupService {
    private ApiClient apiClient;

    public SignupService() {
        this.apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
    }

    public void signup(SignupRequest signupRequest, final SignupCallback callback) {
        Call<SignupResponse> call = this.apiClient.signup(signupRequest);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
                else {
                    callback.onError(new Throwable("Signup failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface SignupCallback {
        void onSuccess(SignupResponse signupResponse);
        void onError(Throwable throwable);
    }
}
