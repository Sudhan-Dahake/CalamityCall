










package com.example.calamitycall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1","notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notificationButtonCritical(View view){

        final String CHANNEL_ID = "channel1";

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.critical_icon);


        Intent activityCancelIntent = new Intent(this,MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(this,0,activityCancelIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Critical Alert")
                //.setContentTitle("Tornado Spotted")
//                .setContentText("Tornado Spotted")
                .setLargeIcon(largeIcon)
                .addAction(R.mipmap.ic_launcher,"Go to the app",cancelContentIntent)
                .setColor(Color.BLUE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Critical Alert")
                        .bigText("Tornado Spotted                                                                  " +
                                "\n\nLocation: Kitchener \nSent From: Emergency Services" +
                                "\n Latitude: 43.4516\nLongitude: 43.4516"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,builder.build());

    }










    public void notificationButtonUrgent(View view){

        final String CHANNEL_ID = "channel1";

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.urgent_icon);


        Intent activityCancelIntent = new Intent(this,MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(this,0,activityCancelIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Urgent Alert")
                .setLargeIcon(largeIcon)
                .addAction(R.mipmap.ic_launcher,"Go to the app",cancelContentIntent)
                .setColor(Color.BLUE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Urgent Alert")
                        .bigText("High Chance of Tornado                                                                  " +
                                "\n\nLocation: Kitchener \nSent From: Natural Weather System" +
                                "\n Latitude: 43.4516\nLongitude: 43.4516"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,builder.build());

    }





    public void notificationButtonWarning(View view){

        final String CHANNEL_ID = "channel1";

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.warning_icon);


        Intent activityCancelIntent = new Intent(this,MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(this,0,activityCancelIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Warning Alert")
                .setLargeIcon(largeIcon)
                .addAction(R.mipmap.ic_launcher,"Go to the app",cancelContentIntent)
                .setColor(Color.BLUE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Warning Alert")
                        .bigText("Medium Chance of Tornado                                                                  " +
                                "\n\nLocation: Kitchener \nSent From: Natural Weather System" +
                                "\n Latitude: 43.4516\nLongitude: 43.4516"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,builder.build());

    }


    public void notificationButtonWatch(View view){

        final String CHANNEL_ID = "channel1";

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.watch_icon);


        Intent activityCancelIntent = new Intent(this,MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(this,0,activityCancelIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Watch Alert")
                .setLargeIcon(largeIcon)
                .addAction(R.mipmap.ic_launcher,"Go to the app",cancelContentIntent)
                .setColor(Color.BLUE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Watch Alert")
                        .bigText("Low Chance of Tornado                                                                  " +
                                "\n\nLocation: Kitchener \nSent From: Natural Weather System" +
                                "\n Latitude: 43.4516\nLongitude: 43.4516"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,builder.build());

    }










}

























































//package com.example.calamitycall;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//public class MainActivity extends AppCompatActivity {
//
//
//    // declaring a TextView variable, this is so that we can replace the disaster type's name in the notification
//    private TextView disasterTypeTextView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("channel1","notification", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//
//        // Example: New disaster type received
//        String latestPacket = "Tornado";  // This would be your new packet
//
//        updateDisasterType(latestPacket);
//    }
//
//
//
//// using this method we can get access to the elements in basic_notif_critical.xml file
//    private void switchToBasicNotifCritical() {
//        setContentView(R.layout.basic_notif_critical);
//
//        disasterTypeTextView = findViewById(R.id.disaster_type_critical);
//    }
//
//
//
//
//
//
//
//
//
//
//
//    // this is the default notification management for android
//
////
////    public void notificationButton(View view) {
////        // Define the color for the notification background (e.g., Bright Red)
////        int color = getResources().getColor(R.color.notification_color, null);
////
////        final String CHANNEL_ID = "channel1";
////
////        // Create the notification
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setSmallIcon(R.drawable.logo)
////                .setContentTitle("Notification Title")
////                .setContentText("This is a critical notification")
////                .setPriority(NotificationCompat.PRIORITY_HIGH)
////                .setColor(color)
////                .setColorized(true); // Use this to colorize the notification on supported devices
////
////        // Notify using the notification manager
////        NotificationManager notificationManager = getSystemService(NotificationManager.class);
////        notificationManager.notify(1, builder.build());
////    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    // Method to update the TextView for the disaster type for critical alerts
//    public void updateDisasterType(String latestPacket) {
//        // Replace "Tornado" with the variable value and append " spotted"
//        String displayText = latestPacket + " spotted";  // Concatenate the strings
//        disasterTypeTextView.setText(displayText);  // Set the text to the TextView
//    }
//}