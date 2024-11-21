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
    public void testGetAndSetCriticalNotificationOnFalse() {
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
}
