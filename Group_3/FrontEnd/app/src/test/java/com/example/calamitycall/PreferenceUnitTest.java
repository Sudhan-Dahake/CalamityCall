package com.example.calamitycall;

import com.example.calamitycall.models.preference.Flashing;
import com.example.calamitycall.models.preference.Noise;
import com.example.calamitycall.models.preference.NotificationAlertType;
import com.example.calamitycall.models.preference.NotificationOn;
import com.example.calamitycall.models.preference.TextToSpeech;

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

    /*** Happy Path Tests for NotificationAlertType class ***/

    @Test
    public void testNotificationAlertTypeConstructorAndGetters() {
        // Create a NotificationAlertType object with all parameters
        NotificationAlertType alertType = new NotificationAlertType(5, "Low", "Medium", "High", "Critical");

        // Validate that all fields are initialized correctly
        assertEquals(5, alertType.getPreferenceid());
        assertEquals("Low", alertType.getWatch());
        assertEquals("Medium", alertType.getWarning());
        assertEquals("High", alertType.getUrgent());
        assertEquals("Critical", alertType.getCritical());
    }

    @Test
    public void testNotificationAlertTypeSetters() {
        // Create a NotificationAlertType object with empty strings
        NotificationAlertType alertType = new NotificationAlertType("", "", "", "");

        // Update fields using setters
        alertType.setPreferenceid(6);
        alertType.setWatch("UpdatedLow");
        alertType.setWarning("UpdatedMedium");
        alertType.setUrgent("UpdatedHigh");
        alertType.setCritical("UpdatedCritical");

        // Validate that the fields have been updated correctly
        assertEquals(6, alertType.getPreferenceid());
        assertEquals("UpdatedLow", alertType.getWatch());
        assertEquals("UpdatedMedium", alertType.getWarning());
        assertEquals("UpdatedHigh", alertType.getUrgent());
        assertEquals("UpdatedCritical", alertType.getCritical());
    }

    /*** Sad Path Tests for NotificationAlertType class ***/

    @Test
    public void testNotificationAlertTypeDefaultValues() {
        // Create a NotificationAlertType object with empty strings
        NotificationAlertType alertType = new NotificationAlertType("", "", "", "");

        // Validate that preferenceid defaults to 0 and string fields are empty
        assertEquals(0, alertType.getPreferenceid());
        assertEquals("", alertType.getWatch());
        assertEquals("", alertType.getWarning());
        assertEquals("", alertType.getUrgent());
        assertEquals("", alertType.getCritical());
    }

    /*** Happy Path Tests for NotificationOn class ***/

    @Test
    public void testNotificationOnConstructorAndGetters() {
        // Create a NotificationOn object using the constructor with all parameters
        NotificationOn notificationOn = new NotificationOn(7, true, true, false, false);

        // Validate that all fields are initialized correctly
        assertEquals(7, notificationOn.getPreferenceid());
        assertTrue(notificationOn.getWatch());
        assertTrue(notificationOn.getWarning());
        assertFalse(notificationOn.getUrgent());
        assertFalse(notificationOn.getCritical());
    }

    @Test
    public void testNotificationOnSetters() {
        // Create a NotificationOn object using the constructor without preferenceid
        NotificationOn notificationOn = new NotificationOn(false, false, false, false);

        // Update fields using setters
        notificationOn.setPreferenceid(8);
        notificationOn.setWatch(true);
        notificationOn.setWarning(false);
        notificationOn.setUrgent(true);
        notificationOn.setCritical(false);

        // Validate that the fields have been updated correctly
        assertEquals(8, notificationOn.getPreferenceid());
        assertTrue(notificationOn.getWatch());
        assertFalse(notificationOn.getWarning());
        assertTrue(notificationOn.getUrgent());
        assertFalse(notificationOn.getCritical());
    }

    /*** Sad Path Tests for NotificationOn class ***/

    @Test
    public void testNotificationOnDefaultValues() {
        // Create a NotificationOn object with all boolean fields set to false
        NotificationOn notificationOn = new NotificationOn(false, false, false, false);

        // Validate that preferenceid defaults to 0 and boolean fields are false
        assertEquals(0, notificationOn.getPreferenceid());
        assertFalse(notificationOn.getWatch());
        assertFalse(notificationOn.getWarning());
        assertFalse(notificationOn.getUrgent());
        assertFalse(notificationOn.getCritical());
    }

    /*** Happy Path Tests for TextToSpeech class ***/

    @Test
    public void testTextToSpeechConstructorAndGetters() {
        // Create a TextToSpeech object using the constructor with all parameters
        TextToSpeech textToSpeech = new TextToSpeech(9, true, false, true, false);

        // Validate that all fields are initialized correctly
        assertEquals(9, textToSpeech.getPreferenceid());
        assertTrue(textToSpeech.getWatch());
        assertFalse(textToSpeech.getWarning());
        assertTrue(textToSpeech.getUrgent());
        assertFalse(textToSpeech.getCritical());
    }

    @Test
    public void testTextToSpeechSetters() {
        // Create a TextToSpeech object using the constructor without preferenceid
        TextToSpeech textToSpeech = new TextToSpeech(false, false, false, false);

        // Update fields using setters
        textToSpeech.setPreferenceid(10);
        textToSpeech.setWatch(true);
        textToSpeech.setWarning(true);
        textToSpeech.setUrgent(false);
        textToSpeech.setCritical(true);

        // Validate that the fields have been updated correctly
        assertEquals(10, textToSpeech.getPreferenceid());
        assertTrue(textToSpeech.getWatch());
        assertTrue(textToSpeech.getWarning());
        assertFalse(textToSpeech.getUrgent());
        assertTrue(textToSpeech.getCritical());
    }

    /*** Sad Path Tests for TextToSpeech class ***/

    @Test
    public void testTextToSpeechDefaultValues() {
        // Create a TextToSpeech object with all boolean fields set to false
        TextToSpeech textToSpeech = new TextToSpeech(false, false, false, false);

        // Validate that preferenceid defaults to 0 and boolean fields are false
        assertEquals(0, textToSpeech.getPreferenceid());
        assertFalse(textToSpeech.getWatch());
        assertFalse(textToSpeech.getWarning());
        assertFalse(textToSpeech.getUrgent());
        assertFalse(textToSpeech.getCritical());
    }

}
