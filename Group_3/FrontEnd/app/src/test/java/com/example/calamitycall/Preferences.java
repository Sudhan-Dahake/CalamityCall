package com.example.calamitycall;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Preferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SettingsPreferences settingsPreferences;
    private Map<String, Object> preferencesMap;

    @Before
    public void setUp() {
        // Mock the Context and SharedPreferences
        Context context = mock(Context.class);
        sharedPreferences = mock(SharedPreferences.class);
        editor = mock(SharedPreferences.Editor.class);

        // Simulate SharedPreferences behavior using a Map
        preferencesMap = new HashMap<>();

        // Mock SharedPreferences.Editor methods
        when(editor.putBoolean(anyString(), anyBoolean())).thenAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Boolean value = invocation.getArgument(1);
            preferencesMap.put(key, value);
            return editor;
        });

        doNothing().when(editor).apply();

        // Mock SharedPreferences methods
        when(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Boolean defaultValue = invocation.getArgument(1);
            return (Boolean) preferencesMap.getOrDefault(key, defaultValue);
        });

        when(sharedPreferences.edit()).thenReturn(editor);

        // Mock Context behavior
        when(context.getSharedPreferences("NotificationPreferences", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);

        // Initialize the SettingsPreferences
        settingsPreferences = new SettingsPreferences(context);
    }

    @Test
    public void testIsCriticalNotificationOn() {
        settingsPreferences.setCriticalNotificationOn(true);


        boolean isNotifEnabled = settingsPreferences.isCriticalNotificationOn();
        assertTrue("Critical notification should be enabled", isNotifEnabled);


        settingsPreferences.setCriticalNotificationOn(false);
        isNotifEnabled = settingsPreferences.isCriticalNotificationOn();
        assertFalse("Critical notification should be disabled", isNotifEnabled);
    }

    @Test
    public void testisWatchNotificationOn() {
        settingsPreferences.setWatchNotificationOn(true);


        boolean isNotifEnabled = settingsPreferences.isWatchNotificationOn();
        assertTrue("Watch notification should be enabled", isNotifEnabled);


        settingsPreferences.setWatchNotificationOn(false);
        isNotifEnabled = settingsPreferences.isWatchNotificationOn();
        assertFalse("Watch notification should be disabled", isNotifEnabled);
    }

    @Test
    public void testisWarningNotificationOn() {
        settingsPreferences.setWarningNotificationOn(true);


        boolean isNotifEnabled = settingsPreferences.isWarningNotificationOn();
        assertTrue("Warning notification should be enabled", isNotifEnabled);


        settingsPreferences.setWarningNotificationOn(false);
        isNotifEnabled = settingsPreferences.isWarningNotificationOn();
        assertFalse("Warning notification should be disabled", isNotifEnabled);
    }


    @Test
    public void testisUrgentNotificationOn() {
        settingsPreferences.setUrgentNotificationOn(true);


        boolean isNotifEnabled = settingsPreferences.isUrgentNotificationOn();
        assertTrue("Urgent notification should be enabled", isNotifEnabled);


        settingsPreferences.setUrgentNotificationOn(false);
        isNotifEnabled = settingsPreferences.isUrgentNotificationOn();
        assertFalse("Urgent notification should be disabled", isNotifEnabled);
    }


    @Test
    public void testisWatchFlashing() {
        settingsPreferences.setWatchFlashingOn(true);


        boolean isFlashingEnabled = settingsPreferences.isWatchFlashingOn();
        assertTrue("Watch flashing should be enabled", isFlashingEnabled);


        settingsPreferences.setWatchFlashingOn(false);
        isFlashingEnabled = settingsPreferences.isWatchFlashingOn();
        assertFalse("Watch flashing should be disabled", isFlashingEnabled);
    }


    @Test
    public void testisWarningFlashing() {
        settingsPreferences.setWarningFlashingOn(true);


        boolean isFlashingEnabled = settingsPreferences.isWarningFlashingOn();
        assertTrue("Warning flashing should be enabled", isFlashingEnabled);


        settingsPreferences.setWarningFlashingOn(false);
        isFlashingEnabled = settingsPreferences.isWarningFlashingOn();
        assertFalse("Warning flashing should be disabled", isFlashingEnabled);
    }

    @Test
    public void testisUrgentFlashing() {
        settingsPreferences.setUrgentFlashingOn(true);


        boolean isFlashingEnabled = settingsPreferences.isUrgentFlashingOn();
        assertTrue("Urgent flashing should be enabled", isFlashingEnabled);


        settingsPreferences.setUrgentFlashingOn(false);
        isFlashingEnabled = settingsPreferences.isUrgentFlashingOn();
        assertFalse("Urgent flashing should be disabled", isFlashingEnabled);
    }

    @Test
    public void testisCriticalFlashing() {
        settingsPreferences.setCriticalFlashingOn(true);


        boolean isFlashingEnabled = settingsPreferences.isCriticalFlashingOn();
        assertTrue("Critical flashing should be enabled", isFlashingEnabled);


        settingsPreferences.setCriticalFlashingOn(false);
        isFlashingEnabled = settingsPreferences.isCriticalFlashingOn();
        assertFalse("Critical flashing should be disabled", isFlashingEnabled);
    }

    @Test
    public void testisWatchNoiseOn() {
        settingsPreferences.setWatchNoiseOn(true);


        boolean isNoiseEnabled = settingsPreferences.isWatchNoiseOn();
        assertTrue("Watch noise should be enabled", isNoiseEnabled);


        settingsPreferences.setWatchNoiseOn(false);
        isNoiseEnabled = settingsPreferences.isWatchNoiseOn();
        assertFalse("Watch noise should be disabled", isNoiseEnabled);
    }

    @Test
    public void testisWarningNoiseOn() {
        settingsPreferences.setWarningNoiseOn(true);


        boolean isNoiseEnabled = settingsPreferences.isWarningNoiseOn();
        assertTrue("Warning noise should be enabled", isNoiseEnabled);


        settingsPreferences.setWarningNoiseOn(false);
        isNoiseEnabled = settingsPreferences.isWarningNoiseOn();
        assertFalse("Warning noise should be disabled", isNoiseEnabled);
    }

    @Test
    public void testisUrgentNoiseOn() {
        settingsPreferences.setUrgentNoiseOn(true);


        boolean isNoiseEnabled = settingsPreferences.isUrgentNoiseOn();
        assertTrue("Urgent noise should be enabled", isNoiseEnabled);


        settingsPreferences.setUrgentNoiseOn(false);
        isNoiseEnabled = settingsPreferences.isWarningNoiseOn();
        assertFalse("Urgent noise should be disabled", isNoiseEnabled);
    }

    @Test
    public void testisCriticalNoiseOn() {
        settingsPreferences.setCriticalNoiseOn(true);


        boolean isNoiseEnabled = settingsPreferences.isCriticalNoiseOn();
        assertTrue("Critical noise should be enabled", isNoiseEnabled);


        settingsPreferences.setCriticalNoiseOn(false);
        isNoiseEnabled = settingsPreferences.isCriticalNoiseOn();
        assertFalse("Critical noise should be disabled", isNoiseEnabled);
    }

}
