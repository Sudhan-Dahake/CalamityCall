package com.example.calamitycall;

import static org.junit.Assert.assertEquals;

import com.example.calamitycall.models.signup.SignupResponse;

import org.junit.Test;

public class SignupResponseTest {

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
