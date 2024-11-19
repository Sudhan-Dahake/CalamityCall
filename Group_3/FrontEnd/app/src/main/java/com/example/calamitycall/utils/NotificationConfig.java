package com.example.calamitycall.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

import com.example.calamitycall.MainActivity;
import com.example.calamitycall.R;
import com.example.calamitycall.SettingsPreferences;

import kotlin.random.URandomKt;

public class NotificationConfig {

    private final Context context;
    private int notificationId = 0;

    // Constructor accepting a Context
    public NotificationConfig(Context context) {
        this.context = context;
    }
    //**** THIS FUNCTION STAYS ****
    // Helper method to create and send notifications
    public void sendNotification(int level, String type, String city, String notifOrigin,Float latitude,
                                 Float longitude, String prepSteps, String activeSteps, String recoverySteps) {

        SettingsPreferences settingsPreferences = new SettingsPreferences(context);
        boolean isNoiseEnabled = false;
        boolean isNotifEnabled = true;
        boolean isTTSEnabled = false;
        boolean isFlashingEnabled = false;
        int collapsedLayoutId = 0;
        int expandedLayoutId = 0;
        String lvlString = "Alert";
        switch(level){
            case 1:
                lvlString = "Watch Alert";
                collapsedLayoutId = R.layout.basic_notif_watch_collapsed;
                expandedLayoutId = R.layout.basic_notif_watch_expanded;
                isNoiseEnabled = settingsPreferences.isWatchNoiseOn();
                isNotifEnabled = settingsPreferences.isWatchNotificationOn();
                //isTTSEnabled = settingsPreferences.isWatchTTSEnabled();
                isFlashingEnabled = settingsPreferences.isWatchFlashingOn();
                break;
            case 2:
                lvlString = "Warning Alert";
                collapsedLayoutId = R.layout.basic_notif_warning_collapsed;
                expandedLayoutId = R.layout.basic_notif_warning_expanded;
                isNoiseEnabled = settingsPreferences.isWarningNoiseOn();
                isNotifEnabled = settingsPreferences.isWarningNotificationOn();
                //isTTSEnabled = settingsPreferences.isWatchTTSEnabled();
                isFlashingEnabled = settingsPreferences.isWarningFlashingOn();
                break;
            case 3:
                lvlString = "Urgent Alert";
                collapsedLayoutId = R.layout.basic_notif_urgent_collapsed;
                expandedLayoutId = R.layout.basic_notif_urgent_expanded;
                isNoiseEnabled = settingsPreferences.isUrgentNoiseOn();
                isNotifEnabled = settingsPreferences.isUrgentNotificationOn();
                //isTTSEnabled = settingsPreferences.isWatchTTSEnabled();
                isFlashingEnabled = settingsPreferences.isUrgentFlashingOn();
                break;
            case 4:
                lvlString = "Critical Alert";
                collapsedLayoutId = R.layout.basic_notif_critical_collapsed;
                expandedLayoutId = R.layout.basic_notif_critical_expanded;
                isNoiseEnabled = settingsPreferences.isCriticalNoiseOn();
                isNotifEnabled = settingsPreferences.isCriticalNotificationOn();
                //isTTSEnabled = settingsPreferences.isWatchTTSEnabled();
                isFlashingEnabled = settingsPreferences.isCriticalFlashingOn();
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
                (city != null ? city + "\n" : "") +
                (notifOrigin != null ? notifOrigin + "\n" : "") +
                (latitude != null ? latitude + "\n" : "") +
                (longitude != null ? longitude + "\n" : "") +
                (prepSteps != null ? prepSteps + "\n" : "") +
                (activeSteps != null ? activeSteps + "\n" : "") +
                (recoverySteps != null ? recoverySteps + "\n" : "");
        // Expanded layout for the push notification
        RemoteViews expandedLayout = new RemoteViews(context.getPackageName(), expandedLayoutId);
        expandedLayout.setTextViewText(R.id.disaster_level, lvlString);
        expandedLayout.setTextViewText(R.id.disaster_type, type);
        expandedLayout.setTextViewText(R.id.notification_details, notifDetails);

        Intent activityCancelIntent = new Intent(context, MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(context, 0, activityCancelIntent, PendingIntent.FLAG_IMMUTABLE);

        expandedLayout.setOnClickPendingIntent(R.id.action_button, cancelContentIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setCustomContentView(collapsedLayout)
                .setCustomBigContentView(expandedLayout)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && isNotifEnabled) {
            notificationManager.notify(notificationId++, builder.build());
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
