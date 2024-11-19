package com.example.calamitycall.firebase;

import com.example.calamitycall.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        if (data.containsKey("force_popup") && "true".equals(data.get("force_popup"))) {
            sendPopUpNotification(data);
        }

        else {
            sendPushNotification(data);
        }
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // Send the new token to your backend server to update the stored token for this device
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        // Send the token to the server here (yet to be implemented).
    }


    private void sendPushNotification(Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "PUSH_CHANNEL_ID")
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private void sendPopUpNotification(Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "POPUP_CHANNEL_ID")
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(getFullScreenIntent(), true)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    private PendingIntent getFullScreenIntent() {
        Intent intent = new Intent(this, MainActivity.class);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
