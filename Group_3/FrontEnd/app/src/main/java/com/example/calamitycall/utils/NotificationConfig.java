package com.example.calamitycall.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import android.hardware.camera2.CameraAccessException;
import com.example.calamitycall.MainActivity;
import com.example.calamitycall.R;
import com.example.calamitycall.SettingsPreferences;

public class NotificationConfig {

    private final Context context;
    private int notificationId = 0;

    // Constructor accepting a Context
    public NotificationConfig(Context context) {
        this.context = context;
    }
    //**** THIS FUNCTION STAYS ****
    // Helper method to create and send notifications
    @SuppressLint("QueryPermissionsNeeded")
    public void sendNotification(int level, String type, String city, String notifOrigin, Float latitude,
                                 Float longitude, String prepSteps, String activeSteps, String recoverySteps) {

        SettingsPreferences settingsPreferences = new SettingsPreferences(context);
        boolean isNoiseEnabled = false;
        boolean isNotifEnabled = true;
        boolean isFlashingEnabled = false;
        boolean isTTSEnabled = true;
        int collapsedLayoutId = 0;
        int expandedLayoutId = 0;
        String lvlString = "Alert";
        switch(level){
            case 1:
                lvlString = "Watch Alert";
                collapsedLayoutId = R.layout.basic_notif_watch_collapsed;
                expandedLayoutId = R.layout.basic_notif_watch_expanded;
                isFlashingEnabled = settingsPreferences.isWatchFlashingOn();
                isNoiseEnabled = settingsPreferences.isWatchNoiseOn();
                isNotifEnabled = settingsPreferences.isWatchNotificationOn();
                isTTSEnabled = settingsPreferences.isWatchTTSOn();
                break;
            case 2:
                lvlString = "Warning Alert";
                collapsedLayoutId = R.layout.basic_notif_warning_collapsed;
                expandedLayoutId = R.layout.basic_notif_warning_expanded;
                isFlashingEnabled = settingsPreferences.isWarningFlashingOn();
                isNoiseEnabled = settingsPreferences.isWarningNoiseOn();
                isNotifEnabled = settingsPreferences.isWarningNotificationOn();
                isTTSEnabled = settingsPreferences.isWarningTTSOn();
                break;
            case 3:
                lvlString = "Urgent Alert";
                collapsedLayoutId = R.layout.basic_notif_urgent_collapsed;
                expandedLayoutId = R.layout.basic_notif_urgent_expanded;
                isFlashingEnabled = settingsPreferences.isUrgentFlashingOn();
                isNoiseEnabled = settingsPreferences.isUrgentNoiseOn();
                isNotifEnabled = settingsPreferences.isUrgentNotificationOn();
                isTTSEnabled = settingsPreferences.isUrgentTTSOn();
                break;
            case 4:
                lvlString = "Critical Alert";
                collapsedLayoutId = R.layout.basic_notif_critical_collapsed;
                expandedLayoutId = R.layout.basic_notif_critical_expanded;
                isFlashingEnabled = settingsPreferences.isCriticalFlashingOn();
                isNoiseEnabled = settingsPreferences.isCriticalNoiseOn();
                isNotifEnabled = settingsPreferences.isCriticalNotificationOn();
                isTTSEnabled = settingsPreferences.isCriticalTTSOn();
                break;
        }
        // Create notification channel
        String channelId = isNoiseEnabled ? "sound_channel" : "silent_channel";
        createNotificationChannel(channelId, isNoiseEnabled);

        // Collapsed layout for the push notification
        RemoteViews collapsedLayout = new RemoteViews(context.getPackageName(), collapsedLayoutId);
        collapsedLayout.setTextViewText(R.id.disaster_level, lvlString);
        collapsedLayout.setTextViewText(R.id.disaster_type, type);

        String notifDetails =
                "<b>Location:</b> " + (city != null ? city + "<br>" : "Unknown") +
                        "<b>Notified From:</b> " + (notifOrigin != null ? notifOrigin + "<br>" : "Unknown") +
                        "<b>Longitude:</b> " + (longitude != null ? longitude + "<br>" : "Unknown") +
                        "<b>Latitude:</b> " + (latitude != null ? latitude + "<br>" : "Unknown") +
                        (prepSteps != null ? "<b>Preparation Steps:</b> " + prepSteps + "<br>" : "") +
                        (activeSteps != null ? "<b>Active Steps:</b> " + activeSteps + "<br>" : "") +
                        (recoverySteps != null ? "<b>Recovery Steps:</b> " + recoverySteps + "<br>" : "");

        // Expanded layout for the push notification
        RemoteViews expandedLayout = new RemoteViews(context.getPackageName(), expandedLayoutId);
        expandedLayout.setTextViewText(R.id.disaster_level, lvlString);
        expandedLayout.setTextViewText(R.id.disaster_type, type);
        expandedLayout.setTextViewText(R.id.notification_details, Html.fromHtml(notifDetails, Html.FROM_HTML_MODE_LEGACY));

        // Intent to open MainActivity and clear the notification
        Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.putExtra("open_tab", "notifications");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

// Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setCustomContentView(collapsedLayout)
                .setCustomBigContentView(expandedLayout)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(contentIntent) // Make the entire notification clickable
                .setAutoCancel(true);           // Clear the notification when clicked



// Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && isNotifEnabled) {
            notificationManager.notify(notificationId++, builder.build());
            if (isFlashingEnabled){
                try {
                    FlashNotification flashNotification = new FlashNotification(context);
                    flashNotification.startFlashing(300, 300); // Flash on for 300ms, off for 300ms
                    // Stop flashing after 5 seconds
                    new Handler(Looper.getMainLooper()).postDelayed(flashNotification::stopFlashing, 5000);
                } catch (CameraAccessException e) {
                    Log.d("NotificationConfig", "Camera access error: " + e.getMessage());
                }
            }
        }


    }

    // **** THIS FUNCTION STAYS ****
    // Create the notification channel for Android 8.0 and above
    public void createNotificationChannel(String channelId, boolean isNoiseEnabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);
                if (existingChannel != null) {
                    notificationManager.deleteNotificationChannel(channelId);
                }
            }

            CharSequence name = isNoiseEnabled ? "Sound Channel" : "Silent Channel";
            String description = "Channel for disaster alert notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setSound(isNoiseEnabled ? RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) : null, null);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}