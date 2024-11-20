package com.example.calamitycall;

import static org.junit.Assert.assertEquals;

import com.example.calamitycall.models.login.LoginRequest;

import org.junit.Test;

public class LoginRequestTest {

    @Test
    public void testLoginRequestConstructorAndGetters() {
        // Arrange
        String expectedUsername = "testUser";
        String expectedPassword = "testPassword";

        // Act
        LoginRequest loginRequest = new LoginRequest(expectedUsername, expectedPassword);

        // Assert
        assertEquals("Username should match the expected value", expectedUsername, loginRequest.getUsername());
        assertEquals("Password should match the expected value", expectedPassword, loginRequest.getPassword());
    }
}
