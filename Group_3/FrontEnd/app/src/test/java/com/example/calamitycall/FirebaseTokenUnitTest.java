package com.example.calamitycall;

import com.example.calamitycall.models.FirebaseToken.RegisterTokenRequest;
import com.example.calamitycall.models.FirebaseToken.RegisterTokenResponse;

import org.junit.Test;

import static org.junit.Assert.*;

public class FirebaseTokenUnitTest {

    /*** Happy Path Tests for RegisterTokenRequest class ***/

    @Test
    public void testRegisterTokenRequestConstructorAndSetters() {
        // Create a valid request object
        RegisterTokenRequest request = new RegisterTokenRequest("abc123token", "device123", 1);

        // Validate fields initialized by the constructor
        assertEquals("abc123token", request.getFcmtoken());
        assertEquals("device123", request.getDeviceid());
        assertEquals(Integer.valueOf(1), request.getUserid());

        // Update fields using setters
        request.setFcmtoken("newToken456");
        request.setDeviceid("newDevice456");
        request.setUserid(2);

        // Validate updated values
        assertEquals("newToken456", request.getFcmtoken());
        assertEquals("newDevice456", request.getDeviceid());
        assertEquals(Integer.valueOf(2), request.getUserid());
    }

    /*** Sad Path Tests for RegisterTokenRequest class ***/

    @Test
    public void testRegisterTokenRequestWithNullValues() {
        // Create a request object with null values
        RegisterTokenRequest request = new RegisterTokenRequest(null, null, 0);

        // Validate that fields are null
        assertNull(request.getFcmtoken());
        assertNull(request.getDeviceid());
        assertEquals(0, request.getUserid().intValue());

        // Update fields to null explicitly
        request.setFcmtoken(null);
        request.setDeviceid(null);
        request.setUserid(-1);  // since userid is int instead of Integer - cannot pass null

        // Validate fields remain null
        assertNull(request.getFcmtoken());
        assertNull(request.getDeviceid());
        assertEquals(-1, request.getUserid().intValue());
    }

    @Test
    public void testRegisterTokenRequestWithEmptyStrings() {
        // Create a request object with empty strings and invalid userid
        RegisterTokenRequest request = new RegisterTokenRequest("", "", -1);

        // Validate that fields are set as provided
        assertEquals("", request.getFcmtoken());
        assertEquals("", request.getDeviceid());
        assertEquals(Integer.valueOf(-1), request.getUserid());

        // Update fields with invalid data
        request.setFcmtoken("");
        request.setDeviceid("");
        request.setUserid(-999);

        // Validate updated values
        assertEquals("", request.getFcmtoken());
        assertEquals("", request.getDeviceid());
        assertEquals(Integer.valueOf(-999), request.getUserid());
    }

}
