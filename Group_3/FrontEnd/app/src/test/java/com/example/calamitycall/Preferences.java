package com.example.calamitycall;

import android.content.Context;
import android.content.SharedPreferences;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class Preferences {

    @Mock
    SharedPreferences mockSharedPreferences;

    @Mock
    SharedPreferences.Editor mockEditor;

    @Mock
    Context mockContext;

    private SettingsPreferences settingsPreferences;

    // Variable to simulate the SharedPreferences state
    private boolean mockNotificationState = false;

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock behavior for SharedPreferences.Editor
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putBoolean(anyString(), anyBoolean())).thenAnswer(invocation -> {
            String key = invocation.getArgument(0);
            boolean value = invocation.getArgument(1);
            if ("critical_notification_on_key".equals(key)) {
                mockNotificationState = value; // Simulate state change in SharedPreferences
            }
            return mockEditor;
        });
        doNothing().when(mockEditor).apply();  // Do nothing when apply() is called

        // Mock the Context to return the mocked SharedPreferences
        when(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);

        // Mock getBoolean to return the current simulated state
        when(mockSharedPreferences.getBoolean(eq("critical_notification_on_key"), anyBoolean()))
                .thenAnswer(invocation -> mockNotificationState); // Return the current simulated state

        // Initialize SettingsPreferences with mocked Context
        settingsPreferences = new SettingsPreferences(mockContext);
    }

    @Test
    public void testGetAndSetCriticalNotificationOn() {
        // Initially setting the value to true
        settingsPreferences.setCriticalNotificationOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isCriticalNotificationOn();

        // Now set it to false
        settingsPreferences.setCriticalNotificationOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isCriticalNotificationOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetWatchNotificationOn() {
        // Initially setting the value to true
        settingsPreferences.setWatchNotificationOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWatchNotificationOn();

        // Now set it to false
        settingsPreferences.setWatchNotificationOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWatchNotificationOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }
    @Test
    public void testGetAndSetWarningNotificationOn() {
        // Initially setting the value to true
        settingsPreferences.setWarningNotificationOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWarningNotificationOn();

        // Now set it to false
        settingsPreferences.setWarningNotificationOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWarningNotificationOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetUrgentNotificationOn() {
        // Initially setting the value to true
        settingsPreferences.setUrgentNotificationOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isUrgentNotificationOn();

        // Now set it to false
        settingsPreferences.setUrgentNotificationOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isUrgentNotificationOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetWatchFlashing() {
        // Initially setting the value to true
        settingsPreferences.setWatchFlashingOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWatchFlashingOn();

        // Now set it to false
        settingsPreferences.setWatchFlashingOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWatchFlashingOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetWarningFlashing() {
        // Initially setting the value to true
        settingsPreferences.setWarningFlashingOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWarningFlashingOn();

        // Now set it to false
        settingsPreferences.setWarningFlashingOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWarningFlashingOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetUrgentFlashing() {
        // Initially setting the value to true
        settingsPreferences.setUrgentFlashingOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isUrgentFlashingOn();

        // Now set it to false
        settingsPreferences.setUrgentFlashingOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isUrgentFlashingOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetCriticalFlashing() {
        // Initially setting the value to true
        settingsPreferences.setCriticalFlashingOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isCriticalFlashingOn();

        // Now set it to false
        settingsPreferences.setCriticalFlashingOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isCriticalFlashingOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetWatchNoise() {
        // Initially setting the value to true
        settingsPreferences.setWatchNoiseOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWatchNoiseOn();

        // Now set it to false
        settingsPreferences.setWatchNoiseOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWatchNoiseOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetWarningNoise() {
        // Initially setting the value to true
        settingsPreferences.setWarningNoiseOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isWarningNoiseOn();

        // Now set it to false
        settingsPreferences.setWarningNoiseOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isWarningNoiseOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetUrgentNoise() {
        // Initially setting the value to true
        settingsPreferences.setUrgentNoiseOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isUrgentNoiseOn();

        // Now set it to false
        settingsPreferences.setUrgentNoiseOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isUrgentNoiseOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }

    @Test
    public void testGetAndSetCriticalNoise() {
        // Initially setting the value to true
        settingsPreferences.setCriticalNoiseOn(true);

        // Verify the value after setting it to true
        boolean isNotifEnabled = settingsPreferences.isCriticalNoiseOn();

        // Now set it to false
        settingsPreferences.setCriticalNoiseOn(false);

        // Verify the value after setting it to false
        boolean isNotifDisabled = settingsPreferences.isCriticalNoiseOn();
        assertFalse(isNotifDisabled);  // After setting, it should be false
    }



}
