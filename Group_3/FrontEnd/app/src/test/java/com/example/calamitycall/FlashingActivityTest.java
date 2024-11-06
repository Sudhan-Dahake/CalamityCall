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
//public class FlashingActivityTest {
//
//    @Test
//    public void testInitialTextViewText() {
//        FlashingActivity activity = Robolectric.buildActivity(FlashingActivity.class).create().get();
//
//        activity.setContentView(R.layout.activity_settings_flashing_page);
//
//        TextView textView = activity.findViewById(R.id.flashing_title);
//        assertEquals("Flashing", textView.getText().toString());
//    }
//}
