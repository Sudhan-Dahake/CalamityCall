package com.example.calamitycall;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
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
    public void testRegisterNavigation() {
        // Click the "Register now!" text
        device.findObject(By.res(PACKAGE_NAME, "registerNowText"))
                .click();

        // Wait for RegisterActivity to load
        boolean registerActivityLoaded = device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);
        assertTrue("RegisterActivity not loaded", registerActivityLoaded);
    }

    @Test
    public void testSuccessfulLogin() {
        // Input username
        device.findObject(By.res(PACKAGE_NAME, "editTextUsername"))
                .setText("testUser");

        // Input password
        device.findObject(By.res(PACKAGE_NAME, "editTextPassword"))
                .setText("password123");

        // Click the login button
        device.findObject(By.res(PACKAGE_NAME, "btnLogin"))
                .click();

        // Wait for MainActivity to load
        boolean mainActivityLoaded = device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), TIMEOUT);
        assertTrue("MainActivity not loaded", mainActivityLoaded);
    }
}
