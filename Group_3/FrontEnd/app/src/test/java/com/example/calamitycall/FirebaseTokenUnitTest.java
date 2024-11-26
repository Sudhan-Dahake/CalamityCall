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

}
