//package com.example.calamitycall;
//
//import android.widget.TextView;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {28}, manifest = "app/manifests/AndroidManifest.xml") // Adjust SDK version as needed
//public class NotificationOnActivityTest {
//
//    @Test
//    public void testInitialTextViewText() {
//        // Create an instance of the NotificationOnActivity
//        NotificationOnActivity activity = Robolectric.buildActivity(NotificationOnActivity.class).create().get();
//
//        // Ensure the activity's view is properly set
////        activity.setContentView(R.layout.activity_settings_notification_on_page); // Ensure correct layout file
//
//        // Find the TextView and verify it has the correct initial text
//        TextView textView = activity.findViewById(R.id.notification_on_title); // Ensure this ID exists
//        assertEquals("Notification On", textView.getText().toString());
//    }
//}
