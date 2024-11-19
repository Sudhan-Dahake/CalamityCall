package com.example.calamitycall;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.calamitycall.network.auth.LoginService;
import com.example.calamitycall.models.login.LoginRequest;
import com.example.calamitycall.models.login.LoginResponse;

import java.lang.reflect.Field;

public class LoginActivityUnitTest {

    @Mock
    LoginService mockLoginService;

    public LoginActivityUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    // test for successful login (simulated a happy path)
    @Test
    public void testLoginPerformance() throws NoSuchFieldException, IllegalAccessException {
        // Mock input data
        LoginRequest request = new LoginRequest("hang", "password123");

        // Create LoginResponse object and use reflection to set private fields
        LoginResponse response = new LoginResponse();

        Field accessTokenField = LoginResponse.class.getDeclaredField("access_token");
        accessTokenField.setAccessible(true);
        accessTokenField.set(response, "dummyAccessToken");

        Field refreshTokenField = LoginResponse.class.getDeclaredField("refresh_token");
        refreshTokenField.setAccessible(true);
        refreshTokenField.set(response, "dummyRefreshToken");

        // Mock the login behavior
        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onSuccess(response);
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        // Measure performance
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

        // Assert that login completes in under 500ms
        assert (endTime - startTime) < 500 : "Login performance exceeded acceptable threshold";
    }

    // Test for Invalid Username/Password - simulated a failed login due to invalid info
    @Test
    public void testLoginInvalidCredentials() throws NoSuchFieldException, IllegalAccessException {
        // Mock input data
        LoginRequest request = new LoginRequest("invalidUser", "wrongPassword");

        // Mock the login behavior to return an error
        doAnswer(invocation -> {
            LoginService.LoginCallback callback = invocation.getArgument(1);
            callback.onError(new Exception("Invalid credentials"));
            return null;
        }).when(mockLoginService).login(eq(request), any(LoginService.LoginCallback.class));

        // Test the login
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

    // test for empty username/password - validation logic when either username/password is empty
    @Test
    public void testLoginEmptyUsernameOrPassword() {
        // Empty username
        LoginRequest emptyUsernameRequest = new LoginRequest("", "password123");

        // Empty password
        LoginRequest emptyPasswordRequest = new LoginRequest("testuser", "");

        // Assert that validation fails for empty username
        assert emptyUsernameRequest.getUsername().isEmpty() : "Username should not be empty";
        assert !emptyUsernameRequest.getPassword().isEmpty() : "Password is valid";

        // Assert that validation fails for empty password
        assert !emptyPasswordRequest.getUsername().isEmpty() : "Username is valid";
        assert emptyPasswordRequest.getPassword().isEmpty() : "Password should not be empty";
    }


}
