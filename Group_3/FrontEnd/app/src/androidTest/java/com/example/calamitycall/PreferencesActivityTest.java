package com.example.calamitycall;


import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PreferencesActivityTest {
    private static final String PACKAGE_NAME = "com.example.calamitycall";
    private static final long TIMEOUT = 5000;

    private UiDevice device;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Launch the app
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);
    }

    @Test
    public void pressSettingsButton() {
        // Locate the Settings button by its accessibility ID
        UiObject2 bottomNav = device.findObject(By.res(PACKAGE_NAME, "bottom_navigation"));
        assertNotNull("Bottom navigation not found", bottomNav);

        // Click the Settings button


        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);

        boolean settingsPageLoaded = device.wait(Until.hasObject(By.res(PACKAGE_NAME, "SettingsPage")), TIMEOUT);
        assertTrue("Settings page not loaded", settingsPageLoaded);
    }

}


