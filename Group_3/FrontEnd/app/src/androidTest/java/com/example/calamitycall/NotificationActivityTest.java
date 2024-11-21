package com.example.calamitycall;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NotificationActivityTest {

    private static final String PACKAGE_NAME = "com.example.calamitycall";
    private static final long TIMEOUT = 5000;

    private UiDevice device;

    @Before
    public void setUp() throws Exception {
        // Arrange: Set up the device and launch the app
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);

        // Act: Perform login
        performLogin();

        // Act: Navigate to the notification page
        navigateToNotificationPage();
    }

    // Helper function for setup()
    private void performLogin() throws Exception {
        // Arrange: Locate login fields and button
        UiObject2 usernameField = device.findObject(By.res(PACKAGE_NAME, "editTextUsername"));
        UiObject2 passwordField = device.findObject(By.res(PACKAGE_NAME, "editTextPassword"));
        UiObject2 loginButton = device.findObject(By.res(PACKAGE_NAME, "btnLogin"));

        // Act: Input credentials and click the login button
        usernameField.setText("testUser");
        passwordField.setText("password123");
        loginButton.click();

        // Assert: Verify the main activity loads successfully
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);
    }

    // Helper function for setup()
    private void navigateToNotificationPage() {
        // Arrange: Wait for bottom navigation to load and locate the notification menu item
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "bottom_navigation")), TIMEOUT);
        UiObject2 notificationMenuItem = device.findObject(By.res(PACKAGE_NAME, "nav_notification"));

        if (notificationMenuItem == null) {
            // Fallback: Locate using text if resource ID is not found
            notificationMenuItem = device.findObject(By.text("Notifications"));
        }

        // Act: Click the notification menu item
        if (notificationMenuItem != null) {
            notificationMenuItem.click();

            // Assert: Verify the notification page loads successfully
            device.wait(Until.hasObject(By.res(PACKAGE_NAME, "notificationPageRoot")), TIMEOUT);
        } else {
            throw new RuntimeException("Failed to locate the notification menu item.");
        }
    }

    @Test
    public void testTabSwitching() {
        // Arrange: Locate the "Active" and "History" tabs
        UiObject2 activeTab = device.findObject(By.text("Active"));
        UiObject2 historyTab = device.findObject(By.text("History"));

        // Act: Click the "Active" tab
        if (activeTab != null) {
            activeTab.click();

            // Assert: Verify the "Active" tab is selected
            assertTrue("Active tab is not selected", activeTab.isSelected());
        }

        // Act: Click the "History" tab
        if (historyTab != null) {
            historyTab.click();

            // Assert: Verify the "History" tab is selected
            assertTrue("History tab is not selected", historyTab.isSelected());
        }
    }

    @Test
    public void testNotificationPageLoadsSuccessfully() {
        // Arrange: Locate a key element unique to the notification page
        UiObject2 notificationTitle = device.findObject(By.text("Alerts"));

        // Act & Assert: Verify the title is displayed
        assertTrue("Notification page title is not displayed", notificationTitle != null);
    }

    @Test
    public void testNavigateBackToHomePage() {
        // Arrange: Locate the Home menu item
        UiObject2 homeMenuItem = device.findObject(By.res(PACKAGE_NAME, "nav_forum"));

        // Act: Click the Home menu item
        homeMenuItem.click();

        // Assert: Verify the Home page loads successfully
        UiObject2 homePageTitle = device.findObject(By.text("Forum"));
        assertTrue("Failed to navigate back to Home page", homePageTitle != null);
    }


}
