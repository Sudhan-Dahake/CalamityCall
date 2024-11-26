package com.example.calamitycall;

import com.example.calamitycall.models.preference.Flashing;

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

}
