package com.example.calamitycall;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.Until;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

public class PreferencesActivityTest {

    private UiDevice device;
    private static final String PACKAGE_NAME = "com.example.calamitycall";
    private static final long TIMEOUT = 5000;


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

        navigateToPreferencesPage();
    }

    private void navigateToPreferencesPage() {
        // Arrange: Wait for bottom navigation to load and locate the notification menu item
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "bottom_navigation")), TIMEOUT);
        UiObject2 notificationMenuItem = device.findObject(By.res(PACKAGE_NAME, "nav_settings"));

        if (notificationMenuItem == null) {
            // Fallback: Locate using text if resource ID is not found
            notificationMenuItem = device.findObject(By.text("Settings"));
        }

        // Act: Click the notification menu item
        if (notificationMenuItem != null) {
            notificationMenuItem.click();

            // Assert: Verify the notification page loads successfully
            device.wait(Until.hasObject(By.res(PACKAGE_NAME, "Settings")), TIMEOUT);
        } else {
            throw new RuntimeException("Failed to locate the settings menu item.");
        }
    }

    @Test
    public void testNotifcationOnPageLoadsSuccessfully() {
        // Arrange: Locate a key element unique to the notification page
        UiObject2 notificationOnTitle = device.findObject(By.text("Notifcation On"));

        notificationOnTitle.click();
        UiObject2 saveTitle = device.findObject(By.text("Save"));
        // Act & Assert: Verify the title is displayed
        assertTrue("Notification page title is not displayed", saveTitle != null);
    }

}