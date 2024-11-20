package com.example.calamitycall;

import static org.junit.Assert.assertEquals;

import com.example.calamitycall.models.login.LoginResponse;

import org.junit.Test;

public class LoginResponseTest {

    @Test
    public void testLoginResponseGetters() {
        // Arrange
        String expectedAccessToken = "accessToken123";
        String expectedRefreshToken = "refreshToken456";
        String expectedTokenType = "Bearer";

        LoginResponse.UserData expectedUser = new LoginResponse.UserData(
                "testUser", 101, 25, "123 Test Street", "12345", "Test City"
        );

        LoginResponse loginResponse = new LoginResponse(
                expectedAccessToken, expectedRefreshToken, expectedTokenType, expectedUser
        );

        // Act & Assert
        assertEquals("Access token should match the expected value", expectedAccessToken, loginResponse.getAccessToken());
        assertEquals("Refresh token should match the expected value", expectedRefreshToken, loginResponse.getRefreshToken());
        assertEquals("Token type should match the expected value", expectedTokenType, loginResponse.getTokenType());
        assertEquals("User should match the expected UserData instance", expectedUser, loginResponse.getUser());
    }

    @Test
    public void testUserDataGetters() {
        // Arrange
        String expectedUsername = "testUser";
        int expectedPreferenceID = 101;
        int expectedAge = 25;
        String expectedAddress = "123 Test Street";
        String expectedZipCode = "12345";
        String expectedCity = "Test City";

        LoginResponse.UserData userData = new LoginResponse.UserData(
                expectedUsername, expectedPreferenceID, expectedAge, expectedAddress, expectedZipCode, expectedCity
        );

        // Act & Assert
        assertEquals("Username should match the expected value", expectedUsername, userData.getUsername());
        assertEquals("Preference ID should match the expected value", expectedPreferenceID, userData.getPreferenceID());
        assertEquals("Age should match the expected value", expectedAge, userData.getAge());
        assertEquals("Address should match the expected value", expectedAddress, userData.getAddress());
        assertEquals("Zip code should match the expected value", expectedZipCode, userData.getZipCode());
        assertEquals("City should match the expected value", expectedCity, userData.getCity());
    }
}
