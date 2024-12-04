package com.example.calamitycall.network;

import com.example.calamitycall.models.NotificationHistory.NotificationHistoryRequest;
import com.example.calamitycall.models.NotificationHistory.NotificationHistoryResponse;
import com.example.calamitycall.models.signup.SignupRequest;
import com.example.calamitycall.models.signup.SignupResponse;
import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenRequest;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenResponse;
import com.example.calamitycall.models.preference.PreferenceResponse;
import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.models.preference.PreferenceUpdateRequest;
import com.example.calamitycall.models.preference.PreferenceUpdateResponse;
import com.example.calamitycall.models.token.VerifyTokenResponse;
import com.example.calamitycall.DisasterReport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiClient {
    @POST("/auth/signup")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/firebase/registertoken")
    Call<RegisterTokenResponse> registerToken(@Body RegisterTokenRequest registerTokenRequest);

    @GET("/preferences/get")
    Call<PreferenceResponse> getPreferences(@Header("Authorization") String authHeader);

    @GET("/protected/verifytoken")
    Call<VerifyTokenResponse> verifyToken(@Header("Authorization") String authHeader);

    @POST("/generate/JWT")
    Call<TokenResponse> refreshToken(@Body TokenGenerateRequest tokenGenerateRequest);

    @POST("/preferences/update")
    Call<PreferenceUpdateResponse> updatePreference(@Header("Authorization") String authHeader, @Body PreferenceUpdateRequest<Object> preferenceUpdateRequest);

    @POST("/notifications/history")
    Call<NotificationHistoryResponse> getNotificationHistory(
            @Body NotificationHistoryRequest request
    );
    @POST("/report/report")
    Call<Void> submitReport(
            @Body DisasterReport report
    );
}