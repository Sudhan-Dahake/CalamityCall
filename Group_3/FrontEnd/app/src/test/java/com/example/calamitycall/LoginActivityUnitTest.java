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
}
