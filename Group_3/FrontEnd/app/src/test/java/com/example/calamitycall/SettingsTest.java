//package com.example.calamitycall;
//
//import android.content.Intent;
//import android.os.Build;
//import android.widget.Button;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertEquals;
//import static org.robolectric.Shadows.shadowOf;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(
//        manifest = "FrontEnd/app/src/main/AndroidManifest.xml",  // Update to the correct path to your manifest
//        sdk = Build.VERSION_CODES.P // Adjust SDK version as needed
//        //application = Settings.class, // Include your application class if applicable
//        //qualifiers = "v21" // Optional: set screen size, density, or language as needed
//)
//public class SettingsTest {
//
//    private Settings settingsActivity;
//
//    @Before
//    public void setUp() {
//        // Create the Settings activity before each test with a specific theme
//        settingsActivity = Robolectric.buildActivity(Settings.class)
//                .create()
//                .resume()  // Resume activity lifecycle for button clickability
//                .get();
//        settingsActivity.setTheme(androidx.appcompat.R.style.Theme_AppCompat);
//    }
//
//    @Test
//    public void testButtonsArePresent() {
//        // Check if the buttons are present in the activity
//        Button notificationOnButton = settingsActivity.findViewById(R.id.notification_on);
//        Button notificationAlertTypeButton = settingsActivity.findViewById(R.id.notification_alert_type);
//        Button flashingButton = settingsActivity.findViewById(R.id.flashing);
//        Button noiseButton = settingsActivity.findViewById(R.id.noise);
//
//        assertNotNull(notificationOnButton);
//        assertNotNull(notificationAlertTypeButton);
//        assertNotNull(flashingButton);
//        assertNotNull(noiseButton);
//    }
//
//    @Test
//    public void testNotificationOnButtonStartsNotificationOnActivity() {
//        // Simulate a button click
//        Button notificationOnButton = settingsActivity.findViewById(R.id.notification_on);
//        notificationOnButton.performClick();
//
//        // Verify that the NotificationOnActivity is started
//        Intent intent = shadowOf(settingsActivity).getNextStartedActivity();
//        assertNotNull(intent);
//        assertEquals(NotificationOnActivity.class.getName(), intent.getComponent().getClassName());
//    }
//
//    @Test
//    public void testNotificationAlertTypeButtonStartsNotificationTypeActivity() {
//        // Simulate a button click
//        Button notificationAlertTypeButton = settingsActivity.findViewById(R.id.notification_alert_type);
//        notificationAlertTypeButton.performClick();
//
//        // Verify that the NotificationTypeActivity is started
//        Intent intent = shadowOf(settingsActivity).getNextStartedActivity();
//        assertNotNull(intent);
//        assertEquals(NotificationTypeActivity.class.getName(), intent.getComponent().getClassName());
//    }
//
//    @Test
//    public void testFlashingButtonStartsFlashingActivity() {
//        // Simulate a button click
//        Button flashingButton = settingsActivity.findViewById(R.id.flashing);
//        flashingButton.performClick();
//
//        // Verify that the FlashingActivity is started
//        Intent intent = shadowOf(settingsActivity).getNextStartedActivity();
//        assertNotNull(intent);
//        assertEquals(FlashingActivity.class.getName(), intent.getComponent().getClassName());
//    }
//
//    @Test
//    public void testNoiseButtonStartsNoiseActivity() {
//        // Simulate a button click
//        Button noiseButton = settingsActivity.findViewById(R.id.noise);
//        noiseButton.performClick();
//
//        // Verify that the NoiseActivity is started
//        Intent intent = shadowOf(settingsActivity).getNextStartedActivity();
//        assertNotNull(intent);
//        assertEquals(NoiseActivity.class.getName(), intent.getComponent().getClassName());
//    }
//}
