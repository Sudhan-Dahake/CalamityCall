package com.example.calamitycall;

import com.example.calamitycall.models.token.TokenGenerateRequest;
import com.example.calamitycall.models.token.TokenResponse;
import com.example.calamitycall.models.token.VerifyTokenResponse;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenUnitTest {

    /*** Happy Path Tests for TokenGenerateRequest class ***/

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

    /*** Happy Path Tests for TokenResponse class ***/

    @Test
    public void testTokenResponseConstructor() {
        // Create a response object with valid tokens
        TokenResponse response = new TokenResponse("access_token_123", "Bearer");

        // Validate the access token and token type
        assertEquals("access_token_123", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
    }

    @Test
    public void testTokenResponseSettersAndGetters() {
        // Create a response object
        TokenResponse response = new TokenResponse("initial_token", "InitialType");

        // Update the fields using setters
        response.setAccessToken("new_access_token");
        response.setTokenType("UpdatedType");

        // Validate the updated fields
        assertEquals("new_access_token", response.getAccessToken());
        assertEquals("UpdatedType", response.getTokenType());
    }

    /*** Sad Path Tests for TokenResponse class ***/

    @Test
    public void testTokenResponseWithNullFields() {
        // Create a response object with null fields
        TokenResponse response = new TokenResponse(null, null);

        // Validate that fields are null
        assertNull(response.getAccessToken());
        assertNull(response.getTokenType());

        // Update the fields to null explicitly
        response.setAccessToken(null);
        response.setTokenType(null);

        // Validate that the fields remain null
        assertNull(response.getAccessToken());
        assertNull(response.getTokenType());
    }

    @Test
    public void testTokenResponseWithEmptyFields() {
        // Create a response object with empty strings
        TokenResponse response = new TokenResponse("", "");

        // Validate that fields are empty strings
        assertEquals("", response.getAccessToken());
        assertEquals("", response.getTokenType());

        // Update the fields with empty strings
        response.setAccessToken("");
        response.setTokenType("");

        // Validate the updated fields
        assertEquals("", response.getAccessToken());
        assertEquals("", response.getTokenType());
    }

    /*** Happy Path Tests for VerifyTokenResponse class ***/

    @Test
    public void testVerifyTokenResponseConstructor() {
        // Create a response object with valid message and username
        VerifyTokenResponse response = new VerifyTokenResponse("Token verified", "test_user");

        // Validate the message and username
        assertEquals("Token verified", response.getMessage());
        assertEquals("test_user", response.getUsername());
    }

    @Test
    public void testVerifyTokenResponseSettersAndGetters() {
        // Create a response object
        VerifyTokenResponse response = new VerifyTokenResponse("InitialMessage", "InitialUser");

        // Update the fields using setters
        response.setMessage("UpdatedMessage");
        response.setUsername("UpdatedUser");

        // Validate the updated fields
        assertEquals("UpdatedMessage", response.getMessage());
        assertEquals("UpdatedUser", response.getUsername());
    }

    /*** Sad Path Tests for VerifyTokenResponse class ***/

    @Test
    public void testVerifyTokenResponseWithNullFields() {
        // Create a response object with null fields
        VerifyTokenResponse response = new VerifyTokenResponse(null, null);

        // Validate that fields are null
        assertNull(response.getMessage());
        assertNull(response.getUsername());

        // Update the fields to null explicitly
        response.setMessage(null);
        response.setUsername(null);

        // Validate that the fields remain null
        assertNull(response.getMessage());
        assertNull(response.getUsername());
    }

    @Test
    public void testVerifyTokenResponseWithEmptyFields() {
        // Create a response object with empty strings
        VerifyTokenResponse response = new VerifyTokenResponse("", "");

        // Validate that fields are empty strings
        assertEquals("", response.getMessage());
        assertEquals("", response.getUsername());

        // Update the fields with empty strings
        response.setMessage("");
        response.setUsername("");

        // Validate the updated fields
        assertEquals("", response.getMessage());
        assertEquals("", response.getUsername());
    }
}
