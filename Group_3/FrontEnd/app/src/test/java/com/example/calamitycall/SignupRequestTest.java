package com.example.calamitycall;

import static org.junit.Assert.assertEquals;

import com.example.calamitycall.models.signup.SignupRequest;

import org.junit.Test;

public class SignupRequestTest {

    @Test
    public void testSignupRequestGetters() {
        // Arrange: Set up expected values
        String expectedUsername = "testUser";
        String expectedPassword = "securePassword123";
        int expectedAge = 25;
        String expectedAddress = "123 Test Street";
        String expectedZipCode = "12345";
        String expectedCity = "Test City";

        // Act: Create a SignupRequest object with the expected values
        SignupRequest signupRequest = new SignupRequest(
                expectedUsername, expectedPassword, expectedAge, expectedAddress, expectedZipCode, expectedCity
        );

        // Assert: Verify that the getter methods return the expected values
        assertEquals("Username should match the expected value", expectedUsername, signupRequest.getUsername());
        assertEquals("Password should match the expected value", expectedPassword, signupRequest.getPassword());
        assertEquals("Age should match the expected value", expectedAge, signupRequest.getAge());
        assertEquals("Address should match the expected value", expectedAddress, signupRequest.getAddress());
        assertEquals("Zip code should match the expected value", expectedZipCode, signupRequest.getZipCode());
        assertEquals("City should match the expected value", expectedCity, signupRequest.getCity());
    }
}
