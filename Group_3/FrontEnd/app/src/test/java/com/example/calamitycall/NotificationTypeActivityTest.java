//package com.example.calamitycall;
//
//import static org.junit.Assert.assertEquals;
//
//import android.widget.TextView;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {28}, manifest = "app/manifests/AndroidManifest.xml") // Adjust SDK version as needed
//public class NotificationTypeActivityTest {
//
//    @Test
//    public void testInitialTextViewText() {
//        // Create an instance of the NotificationOnActivity
//        NotificationTypeActivity activity = Robolectric.buildActivity(NotificationTypeActivity.class).create().get();
//
//        // Ensure the activity's view is properly set
//        activity.setContentView(R.layout.activity_settings_notification_type_page); // Ensure correct layout file
//
//        // Find the TextView and verify it has the correct initial text
//        TextView textView = activity.findViewById(R.id.notification_type_title); // Ensure this ID exists
//        assertEquals("Notification Type", textView.getText().toString());
//    }
//}
