package com.example.calamitycall;

import static org.junit.Assert.assertEquals;

import com.example.calamitycall.models.signup.SignupRequest;
import com.example.calamitycall.models.signup.SignupResponse;

import org.junit.Test;

public class SignupUnitTest {

    /*** Happy Path Tests for SignRequest class ***/

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

    /*** Happy Path Tests for SignResponse class ***/

    @Test
    public void testSignupResponseMessage() {
        // Arrange: Set up expected message value
        String expectedMessage = "Signup successful";

        // Act: Create a SignupResponse object and set the message
        SignupResponse signupResponse = new SignupResponse(expectedMessage);
        signupResponse.setMessage(expectedMessage);

        // Assert: Verify that the getter method returns the correct message
        assertEquals("Message should match the expected value", expectedMessage, signupResponse.getMessage());
    }
}
