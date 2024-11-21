package com.example.calamitycall;

import static org.mockito.Mockito.*;

import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.auth.LoginService;
import com.example.calamitycall.utils.TokenManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginServiceTest {

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private TokenManager mockTokenManager;

    @Mock
    private Call<LoginResponse> mockCall;

    private LoginService loginService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inject mocked dependencies
        loginService = new LoginService(mockApiClient, mockTokenManager);
    }

    @Test
    public void testLogin_SuccessfulResponse() {
        // Arrange: Mock successful response
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");
        LoginResponse mockResponse = new LoginResponse("accessToken123", "refreshToken123", "Bearer", null);

        when(mockApiClient.login(loginRequest)).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.success(mockResponse));
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        // Act
        LoginService.LoginCallback mockCallback = mock(LoginService.LoginCallback.class);
        loginService.login(loginRequest, mockCallback);

        // Assert
        verify(mockTokenManager).setAccessToken("accessToken123");
        verify(mockTokenManager).setRefreshToken("refreshToken123");
        verify(mockCallback).onSuccess(mockResponse);
    }

    @Test
    public void testLogin_ErrorResponse() {
        // Arrange: Mock error response
        LoginRequest loginRequest = new LoginRequest("testUser", "wrongPassword");

        // Create a mocked ResponseBody with a valid error message
        ResponseBody errorBody = ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"message\":\"Invalid credentials\"}"
        );

        // Mock the API call to return the error response
        when(mockApiClient.login(loginRequest)).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.error(401, errorBody));
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        // Act: Call the login method
        LoginService.LoginCallback mockCallback = mock(LoginService.LoginCallback.class);
        loginService.login(loginRequest, mockCallback);

        // Assert: Verify the error callback is invoked with the correct error message
        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        verify(mockCallback).onError(captor.capture());
        assert captor.getValue().getMessage().contains("Login failed with code; 401");
    }


    @Test
    public void testLogin_NetworkFailure() {
        // Arrange: Mock network failure
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");
        Throwable networkError = new IOException("Network error");

        when(mockApiClient.login(loginRequest)).thenReturn(mockCall);
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onFailure(mockCall, networkError);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));

        // Act
        LoginService.LoginCallback mockCallback = mock(LoginService.LoginCallback.class);
        loginService.login(loginRequest, mockCallback);

        // Assert
        verify(mockCallback).onError(networkError);
    }
}
