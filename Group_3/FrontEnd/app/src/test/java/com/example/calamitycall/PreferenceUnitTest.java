package com.example.calamitycall;

import com.example.calamitycall.models.preference.Flashing;
import com.example.calamitycall.models.preference.Noise;
import com.example.calamitycall.models.preference.NotificationAlertType;
import com.example.calamitycall.models.preference.NotificationOn;
import com.example.calamitycall.models.preference.PreferenceResponse;
import com.example.calamitycall.models.preference.PreferenceUpdateRequest;
import com.example.calamitycall.models.preference.PreferenceUpdateResponse;
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

    /*** Happy Path Tests for PreferenceResponse class ***/

    @Test
    public void testPreferenceResponseConstructorAndGetters() {
        // Create individual preference objects
        NotificationOn notificationOn = new NotificationOn(1, true, false, true, false);
        Noise noise = new Noise(2, false, true, false, true);
        Flashing flashing = new Flashing(3, true, true, false, false);
        NotificationAlertType alertType = new NotificationAlertType(4, "Low", "Medium", "High", "Critical");
        TextToSpeech textToSpeech = new TextToSpeech(5, true, false, true, false);

        // Create a PreferenceResponse object using all preference objects
        PreferenceResponse response = new PreferenceResponse(notificationOn, noise, flashing, alertType, textToSpeech);

        // Validate that all preference objects are correctly assigned
        assertEquals(notificationOn, response.getNotificationOn());
        assertEquals(noise, response.getNoise());
        assertEquals(flashing, response.getFlashing());
        assertEquals(alertType, response.getNotificationAlertType());
        assertEquals(textToSpeech, response.getTextToSpeech());
    }

    @Test
    public void testPreferenceResponseSetters() {
        // Create a PreferenceResponse object with null values
        PreferenceResponse response = new PreferenceResponse(null, null, null, null, null);

        // Create individual preference objects
        NotificationOn notificationOn = new NotificationOn(1, true, false, true, false);
        Noise noise = new Noise(2, false, true, false, true);
        Flashing flashing = new Flashing(3, true, true, false, false);
        NotificationAlertType alertType = new NotificationAlertType(4, "Low", "Medium", "High", "Critical");
        TextToSpeech textToSpeech = new TextToSpeech(5, true, false, true, false);

        // Set preference objects using setters
        response.setNotificationOn(notificationOn);
        response.setNoise(noise);
        response.setFlashing(flashing);
        response.setNotificationAlertType(alertType);
        response.setTextToSpeech(textToSpeech);

        // Validate that all preference objects are correctly assigned
        assertEquals(notificationOn, response.getNotificationOn());
        assertEquals(noise, response.getNoise());
        assertEquals(flashing, response.getFlashing());
        assertEquals(alertType, response.getNotificationAlertType());
        assertEquals(textToSpeech, response.getTextToSpeech());
    }

    /*** Sad Path Tests for PreferenceResponse class ***/

    @Test
    public void testPreferenceResponseWithNullValues() {
        // Create a PreferenceResponse object with null values
        PreferenceResponse response = new PreferenceResponse(null, null, null, null, null);

        // Validate that all preference objects are null
        assertNull(response.getNotificationOn());
        assertNull(response.getNoise());
        assertNull(response.getFlashing());
        assertNull(response.getNotificationAlertType());
        assertNull(response.getTextToSpeech());
    }

    /*** Happy Path Tests for PreferenceUpdateRequest class ***/

    @Test
    public void testPreferenceUpdateRequestConstructorAndGetters() {
        // Create a data object to be updated (e.g., Flashing)
        Flashing flashing = new Flashing(1, true, false, true, false);

        // Create a PreferenceUpdateRequest with table name and data
        PreferenceUpdateRequest<Flashing> request = new PreferenceUpdateRequest<>("Flashing", flashing);

        // Validate that table name and data are correctly assigned
        assertEquals("Flashing", request.getTableName());
        assertEquals(flashing, request.getData());
    }

    @Test
    public void testPreferenceUpdateRequestSetters() {
        // Create a PreferenceUpdateRequest with initial null values
        PreferenceUpdateRequest<Noise> request = new PreferenceUpdateRequest<>(null, null);

        // Create a data object to be updated
        Noise noise = new Noise(2, false, true, false, true);

        // Set table name and data using setters
        request.setTableName("Noise");
        request.setData(noise);

        // Validate that table name and data are correctly assigned
        assertEquals("Noise", request.getTableName());
        assertEquals(noise, request.getData());
    }

    /*** Sad Path Tests for PreferenceUpdateRequest class ***/

    @Test
    public void testPreferenceUpdateRequestWithNullValues() {
        // Create a PreferenceUpdateRequest with null values
        PreferenceUpdateRequest<TextToSpeech> request = new PreferenceUpdateRequest<>(null, null);

        // Validate that table name and data are null
        assertNull(request.getTableName());
        assertNull(request.getData());
    }

    /*** Happy Path Tests for PreferenceUpdateResponse class ***/

    @Test
    public void testPreferenceUpdateResponseConstructorAndGetters() {
        // Create a response object with a message
        PreferenceUpdateResponse response = new PreferenceUpdateResponse("Update successful");

        // Validate that the message is correctly assigned
        assertEquals("Update successful", response.getMessage());
    }

    @Test
    public void testPreferenceUpdateResponseSetters() {
        // Create a response object with an initial message
        PreferenceUpdateResponse response = new PreferenceUpdateResponse("Initial message");

        // Update the message using setter
        response.setMessage("Update completed");

        // Validate that the message has been updated
        assertEquals("Update completed", response.getMessage());
    }

    /*** Sad Path Tests for PreferenceUpdateResponse class ***/

    @Test
    public void testPreferenceUpdateResponseWithNullMessage() {
        // Create a response object with a null message
        PreferenceUpdateResponse response = new PreferenceUpdateResponse(null);

        // Validate that the message is null
        assertNull(response.getMessage());

        // Set the message to null explicitly
        response.setMessage(null);

        // Validate that the message remains null
        assertNull(response.getMessage());
    }
}
