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
}
