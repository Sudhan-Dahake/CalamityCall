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
//public class NoiseActivityTest {
//
//    @Test
//    public void testInitialTextViewText() {
//        // Create an instance of the NotificationOnActivity
//        NoiseActivity activity = Robolectric.buildActivity(NoiseActivity.class).create().get();
//
//        // Ensure the activity's view is properly set
//        activity.setContentView(R.layout.activity_settings_noise_page); // Ensure correct layout file
////
//        // Find the TextView and verify it has the correct initial text
//        TextView textView = activity.findViewById(R.id.noise_title); // Ensure this ID exists
//        assertEquals("Noise", textView.getText().toString());
//    }
//}
