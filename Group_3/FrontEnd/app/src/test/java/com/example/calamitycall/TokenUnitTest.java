package com.example.calamitycall;

import com.example.calamitycall.models.token.TokenGenerateRequest;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenUnitTest {

    /*** Happy Path Tests for TokenGenerateRequest ***/

    @Test
    public void testTokenGenerateRequestConstructor() {
        // Create a request object with a valid refresh token
        TokenGenerateRequest request = new TokenGenerateRequest("valid_refresh_token");

        // Validate the refresh token
        assertEquals("valid_refresh_token", request.getRefreshToken());
    }

    @Test
    public void testTokenGenerateRequestSettersAndGetters() {
        // Create a request object
        TokenGenerateRequest request = new TokenGenerateRequest("initial_token");

        // Update refresh token using setter
        request.setRefreshToken("updated_token");

        // Validate the updated refresh token
        assertEquals("updated_token", request.getRefreshToken());
    }

    /*** Sad Path Tests for TokenGenerateRequest ***/

    @Test
    public void testTokenGenerateRequestWithNullToken() {
        // Create a request object with a null refresh token
        TokenGenerateRequest request = new TokenGenerateRequest(null);

        // Validate that the refresh token is null
        assertNull(request.getRefreshToken());

        // Set the refresh token to null explicitly
        request.setRefreshToken(null);

        // Validate that the refresh token remains null
        assertNull(request.getRefreshToken());
    }

    @Test
    public void testTokenGenerateRequestWithEmptyToken() {
        // Create a request object with an empty refresh token
        TokenGenerateRequest request = new TokenGenerateRequest("");

        // Validate that the refresh token is an empty string
        assertEquals("", request.getRefreshToken());

        // Update the refresh token with another empty string
        request.setRefreshToken("");

        // Validate the updated refresh token
        assertEquals("", request.getRefreshToken());
    }
}
