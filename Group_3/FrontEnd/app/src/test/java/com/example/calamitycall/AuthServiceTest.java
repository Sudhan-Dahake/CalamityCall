package com.example.calamitycall;

import static org.mockito.Mockito.*;

import android.content.Context;

import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;
import com.example.calamitycall.models.signup.SignupRequest;
import com.example.calamitycall.models.signup.SignupResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.auth.LoginService;
import com.example.calamitycall.network.auth.SignupService;
import com.example.calamitycall.utils.TokenManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AuthServiceTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private Call<LoginResponse> loginCall;

    @Mock
    private Call<SignupResponse> signupCall;

    @Mock
    private Context context;

    private LoginService loginService;
    private SignupService signupService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginService = new LoginService(apiClient, tokenManager);
        signupService = new SignupService();
    }

    /*** Happy Path Test for LoginService ***/
    @Test
    public void testLoginSuccessWithUserData() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");

        // Create UserData mock object with exact required parameters
        LoginResponse.UserData mockUserData = new LoginResponse.UserData(
                "John Doe", 10, 30, "123 Street", "12345", "Waterloo"
        );

        // Create LoginResponse object with the UserData mock
        LoginResponse mockResponse = new LoginResponse(
                "access_token_123",
                "refresh_token_123",
                "Bearer",
                mockUserData
        );

        // Mock API call response
        when(apiClient.login(any())).thenReturn(loginCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(loginCall, Response.success(mockResponse));
            return null;
        }).when(loginCall).enqueue(any());

        // Execute login
        loginService.login(loginRequest, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                // Validate access tokens and token type
                assertEquals("access_token_123", loginResponse.getAccessToken());
                assertEquals("refresh_token_123", loginResponse.getRefreshToken());
                assertEquals("Bearer", loginResponse.getTokenType());

                // Validate UserData fields
                LoginResponse.UserData userData = loginResponse.getUser();
                assertNotNull(userData);
                assertEquals("John Doe", userData.getUsername());
                assertEquals(10, userData.getPreferenceID());
                assertEquals(30, userData.getAge());
                assertEquals("123 Street", userData.getAddress());
                assertEquals("12345", userData.getZipCode());
                assertEquals("Waterloo", userData.getCity());
            }

            @Override
            public void onError(Throwable throwable) {
                fail("Login should succeed");
            }
        });
    }

    /*** Sad Path Tests for LoginService class ***/

    @Test
    public void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");

        // Create a mock ResponseBody for the error response
        ResponseBody errorBody = ResponseBody.create(
                MediaType.parse("application/json"), // Content type
                "{\"error\": \"Unauthorized\"}"      // Mock error message
        );

        // Mock API call response
        when(apiClient.login(any())).thenReturn(loginCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(loginCall, Response.error(401, errorBody));
            return null;
        }).when(loginCall).enqueue(any());

        // Execute login
        loginService.login(loginRequest, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                fail("Login should fail");
            }

            @Override
            public void onError(Throwable throwable) {
                // Validate error response
                assertNotNull(throwable);
                assertTrue(throwable.getMessage().contains("Login failed with code; 401"));
            }
        });
    }

    @Test
    public void testLoginApiFailure() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");

        when(apiClient.login(any())).thenReturn(loginCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onFailure(loginCall, new IOException("Network failure"));
            return null;
        }).when(loginCall).enqueue(any());

        loginService.login(loginRequest, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                fail("Login should fail");
            }

            @Override
            public void onError(Throwable throwable) {
                assertNotNull(throwable);
                assertEquals("Network failure", throwable.getMessage());
            }
        });
    }

    /*** Happy Path Tests for SignupService class ***/

    @Test
    public void testSignupSuccess() {
        SignupRequest signupRequest = new SignupRequest(
                "testuser",
                "password123",
                25,
                "John",
                "Doe",
                "john.doe@example.com"
        );

        // Create mock successful SignupResponse
        SignupResponse mockResponse = new SignupResponse("Signup successful");

        // Mock API call response
        when(apiClient.signup(any())).thenReturn(signupCall);
        doAnswer(invocation -> {
            Callback<SignupResponse> callback = invocation.getArgument(0);
            callback.onResponse(signupCall, Response.success(mockResponse));
            return null;
        }).when(signupCall).enqueue(any());

        // Execute signup
        signupService.signup(signupRequest, new SignupService.SignupCallback() {
            @Override
            public void onSuccess(SignupResponse signupResponse) {
                // Validate response
                assertNotNull(signupResponse);
                assertEquals("Signup successful", signupResponse.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                fail("Signup should succeed");
            }
        });
    }

    /*** Sad Path Tests for SignupService class (Error Response) ***/
    @Test
    public void testSignupErrorResponse() {
        SignupRequest signupRequest = new SignupRequest(
                "testuser",
                "password123",
                25,
                "John",
                "Doe",
                "invalid-email"
        );

        // Mock error response body
        ResponseBody errorBody = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"error\": \"Invalid email format\"}"
        );

        // Mock API call response
        when(apiClient.signup(any())).thenReturn(signupCall);
        doAnswer(invocation -> {
            Callback<SignupResponse> callback = invocation.getArgument(0);
            callback.onResponse(signupCall, Response.error(400, errorBody));
            return null;
        }).when(signupCall).enqueue(any());

        // Execute signup
        signupService.signup(signupRequest, new SignupService.SignupCallback() {
            @Override
            public void onSuccess(SignupResponse signupResponse) {
                fail("Signup should fail");
            }

            @Override
            public void onError(Throwable throwable) {
                // Validate error response
                assertNotNull(throwable);
                assertTrue(throwable.getMessage().contains("Signup failed with code: 400"));
            }
        });
    }

    @Test
    public void testSignupApiFailure() {
        SignupRequest signupRequest = new SignupRequest(
                "testuser",
                "password123",
                25,
                "John",
                "Doe",
                "john.doe@example.com"
        );

        // Mock API call failure
        when(apiClient.signup(any())).thenReturn(signupCall);
        doAnswer(invocation -> {
            Callback<SignupResponse> callback = invocation.getArgument(0);
            callback.onFailure(signupCall, new IOException("Network failure"));
            return null;
        }).when(signupCall).enqueue(any());

        // Execute signup
        signupService.signup(signupRequest, new SignupService.SignupCallback() {
            @Override
            public void onSuccess(SignupResponse signupResponse) {
                fail("Signup should fail");
            }

            @Override
            public void onError(Throwable throwable) {
                // Validate API failure
                assertNotNull(throwable);
                assertEquals("Network failure", throwable.getMessage());
            }
        });
    }

}
