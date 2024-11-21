package com.example.calamitycall;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.Until;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        // Wait for the bottom navigation to load
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "bottom_navigation")), TIMEOUT);

        // Locate the settings menu item by resource ID or fallback to text-based search
        UiObject2 settingsMenuItem = device.findObject(By.res(PACKAGE_NAME, "nav_settings"));

        settingsMenuItem.click();

    }


    @Test
    public void testNotifcationOnPageLoadsSuccessfully() {

        UiObject2 notificationOnTitle = device.findObject(By.res(PACKAGE_NAME, "notification_on"));
        assertTrue("Notification On button is not found", notificationOnTitle != null);

        // Click the button
        notificationOnTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "notificationon_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "notificationon_save"));

        assertTrue("Notification On page title (Save button) is not displayed", saveTitle != null);
        // Locate the save button
    }
    @Test
    public void testNoisePageLoadsSuccessfully() {

        UiObject2 noiseTitle = device.findObject(By.res(PACKAGE_NAME, "noise"));
        assertTrue("Noise button is not found", noiseTitle != null);

        // Click the button
        noiseTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "noise_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "noise_save"));

        assertTrue("Noise page title (Save button) is not displayed", saveTitle != null);
        // Locate the save button
    }

    @Test
    public void testNotificationTypePageLoadsSuccessfully() {

        UiObject2 notificationtypeTitle = device.findObject(By.res(PACKAGE_NAME, "notification_alert_type"));
        assertTrue("Notification Type button is not found", notificationtypeTitle != null);

        // Click the button
        notificationtypeTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "notificationtype_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "notificationtype_save"));

        assertTrue("Notification Type page title (Save button) is not displayed", saveTitle != null);
        // Locate the save button
    }

    @Test
    public void testFlashingPageLoadsSuccessfully() {

        UiObject2 flashingTitle = device.findObject(By.res(PACKAGE_NAME, "flashing"));
        assertTrue("Flashing button is not found", flashingTitle != null);

        // Click the button
        flashingTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "flashing_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "flashing_save"));

        assertTrue("Flashing page title (Save button) is not displayed", saveTitle != null);
        // Locate the save button
    }

    @Test
    public void testTTSPageLoadsSuccessfully() {

        UiObject2 ttsTitle = device.findObject(By.res(PACKAGE_NAME, "text_to_speech"));
        assertTrue("Flashing button is not found", ttsTitle != null);

        // Click the button
        ttsTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "text_to_speech_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "text_to_speech_save"));

        assertTrue("TextToSpeech page title (Save button) is not displayed", saveTitle != null);
        // Locate the save button
    }

    @Test
    public void testNotifcationOnSettingsSaveSuccessfully() {

        UiObject2 notificationOnTitle = device.findObject(By.res(PACKAGE_NAME, "notification_on"));
        // Click the button
        notificationOnTitle.click();

        // Wait for the save button to appear
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "notificationon_save")), TIMEOUT);

        UiObject2 saveTitle = device.findObject(By.res(PACKAGE_NAME, "notificationon_save"));

        UiObject2 watchSettingBefore = device.findObject(By.res(PACKAGE_NAME, "watch_switch"));
        watchSettingBefore.click();

        // Click the button
        saveTitle.click();

        UiObject2 watchSettingAfter = device.findObject(By.res(PACKAGE_NAME, "watch_switch"));

        boolean stateBefore = watchSettingBefore.isChecked();
        boolean stateAfter = watchSettingAfter.isChecked();
        assertEquals(stateBefore, stateAfter);
    }
}
