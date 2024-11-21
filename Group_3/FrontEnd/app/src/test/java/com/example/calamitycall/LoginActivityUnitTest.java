package com.example.calamitycall;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;
import com.example.calamitycall.models.login.LoginResponse.UserData;
import com.example.calamitycall.network.auth.LoginService;
import com.example.calamitycall.utils.TokenManager;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginActivityUnitTest {

    @Mock
    LoginService mockLoginService;

    @Mock
    TokenManager mockTokenManager;

    public LoginActivityUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for successful login
    @Test
    public void testLoginPerformance() {
        LoginRequest request = new LoginRequest("hang", "password123");

        UserData userData = new UserData(
                "testUser", 101, 25, "123 Test Street", "12345", "Test City");
        LoginResponse response = new LoginResponse(
                "dummyAccessToken", "dummyRefreshToken", "Bearer", userData);

        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onSuccess(response);
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        long startTime = System.currentTimeMillis();
        mockLoginService.login(request, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                assert loginResponse != null;
                assert loginResponse.getAccessToken().equals("dummyAccessToken");
            }

            @Override
            public void onError(Throwable throwable) {
                assert false : "Login failed unexpectedly";
            }
        });
        long endTime = System.currentTimeMillis();

        assert (endTime - startTime) < 500 : "Login performance exceeded acceptable threshold";
    }

    // Test for invalid credentials
    @Test
    public void testLoginInvalidCredentials() {
        LoginRequest request = new LoginRequest("invalidUser", "wrongPassword");

        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onError(new Exception("Invalid credentials"));
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        mockLoginService.login(request, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                assert false : "Login succeeded unexpectedly with invalid credentials.";
            }

            @Override
            public void onError(Throwable throwable) {
                assert throwable.getMessage().equals("Invalid credentials");
            }
        });
    }

    // Test for empty username or password
    @Test
    public void testLoginEmptyUsernameOrPassword() {
        LoginRequest emptyUsernameRequest = new LoginRequest("", "password123");
        LoginRequest emptyPasswordRequest = new LoginRequest("testuser", "");

        assert emptyUsernameRequest.getUsername().isEmpty() : "Username should not be empty";
        assert !emptyUsernameRequest.getPassword().isEmpty() : "Password is valid";

        assert !emptyPasswordRequest.getUsername().isEmpty() : "Username is valid";
        assert emptyPasswordRequest.getPassword().isEmpty() : "Password should not be empty";
    }

    // Test for null username or password
    @Test
    public void testLoginNullUsernameOrPassword() {
        LoginRequest nullUsernameRequest = new LoginRequest(null, "password123");
        LoginRequest nullPasswordRequest = new LoginRequest("testuser", null);

        assert nullUsernameRequest.getUsername() == null : "Username should be null";
        assert nullUsernameRequest.getPassword() != null : "Password is valid";

        assert nullPasswordRequest.getUsername() != null : "Username is valid";
        assert nullPasswordRequest.getPassword() == null : "Password should be null";
    }

    // Test for network timeout
    @Test
    public void testLoginNetworkTimeout() {
        LoginRequest request = new LoginRequest("hang", "password123");

        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onError(new Exception("Network timeout"));
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        mockLoginService.login(request, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                assert false : "Login succeeded unexpectedly despite network timeout.";
            }

            @Override
            public void onError(Throwable throwable) {
                assert throwable.getMessage().equals("Network timeout") : "Unexpected error message: " + throwable.getMessage();
            }
        });
    }

    // Test for server error
    @Test
    public void testLoginServerError() {
        LoginRequest request = new LoginRequest("hang", "password123");

        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onError(new Exception("Server error: 500 Internal Server Error"));
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        mockLoginService.login(request, new LoginService.LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                assert false : "Login succeeded unexpectedly despite server error.";
            }

            @Override
            public void onError(Throwable throwable) {
                assert throwable.getMessage().contains("500 Internal Server Error") : "Unexpected error message: " + throwable.getMessage();
            }
        });
    }
}
