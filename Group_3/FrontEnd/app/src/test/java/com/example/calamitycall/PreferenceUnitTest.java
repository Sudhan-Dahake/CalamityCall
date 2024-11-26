package com.example.calamitycall;

import com.example.calamitycall.models.preference.Flashing;
import com.example.calamitycall.models.preference.Noise;

import org.junit.Test;

import static org.junit.Assert.*;

public class PreferenceUnitTest {

    /*** Happy Path Tests for Flashing class ***/

    @Test
    public void testFlashingConstructorAndGetters() {
        // Create a Flashing object using the constructor with all parameters
        Flashing flashing = new Flashing(1, true, false, true, false);

        // Validate that all fields are initialized correctly
        assertEquals(1, flashing.getPreferenceid());
        assertTrue(flashing.getWatch());
        assertFalse(flashing.getWarning());
        assertTrue(flashing.getUrgent());
        assertFalse(flashing.getCritical());
    }

    @Test
    public void testFlashingSetters() {
        // Create a Flashing object using the constructor without preferenceid
        Flashing flashing = new Flashing(true, true, true, true);
        flashing.setPreferenceid(2);
        flashing.setWatch(false);
        flashing.setWarning(false);
        flashing.setUrgent(false);
        flashing.setCritical(false);

        // Update fields using setters
        assertEquals(2, flashing.getPreferenceid());
        assertFalse(flashing.getWatch());
        assertFalse(flashing.getWarning());
        assertFalse(flashing.getUrgent());
        assertFalse(flashing.getCritical());
    }

    /*** Sad Path Tests for Flashing class ***/

    @Test
    public void testFlashingDefaultValues() {
        // Create a Flashing object with all boolean fields set to false
        Flashing flashing = new Flashing(false, false, false, false);

        // Validate that preferenceid defaults to 0 and boolean fields are false
        assertEquals(0, flashing.getPreferenceid());
        assertFalse(flashing.getWatch());
        assertFalse(flashing.getWarning());
        assertFalse(flashing.getUrgent());
        assertFalse(flashing.getCritical());
    }

    /*** Happy Path Tests for Noise class ***/

    @Test
    public void testNoiseConstructorAndGetters() {
        // Create a Noise object using the constructor with all parameters
        Noise noise = new Noise(3, true, false, true, false);

        // Validate that all fields are initialized correctly
        assertEquals(3, noise.getPreferenceid());
        assertTrue(noise.getWatch());
        assertFalse(noise.getWarning());
        assertTrue(noise.getUrgent());
        assertFalse(noise.getCritical());
    }

    @Test
    public void testNoiseSetters() {
        // Create a Noise object using the constructor without preferenceid
        Noise noise = new Noise(false, false, false, false);

        // Update fields using setters
        noise.setPreferenceid(4);
        noise.setWatch(true);
        noise.setWarning(true);
        noise.setUrgent(true);
        noise.setCritical(true);

        // Validate that the fields have been updated correctly
        assertEquals(4, noise.getPreferenceid());
        assertTrue(noise.getWatch());
        assertTrue(noise.getWarning());
        assertTrue(noise.getUrgent());
        assertTrue(noise.getCritical());
    }

    /*** Sad Path Tests for Noise class ***/

    @Test
    public void testNoiseDefaultValues() {
        // Create a Noise object with all boolean fields set to false
        Noise noise = new Noise(false, false, false, false);

        // Validate that preferenceid defaults to 0 and boolean fields are false
        assertEquals(0, noise.getPreferenceid());
        assertFalse(noise.getWatch());
        assertFalse(noise.getWarning());
        assertFalse(noise.getUrgent());
        assertFalse(noise.getCritical());
    }

}
