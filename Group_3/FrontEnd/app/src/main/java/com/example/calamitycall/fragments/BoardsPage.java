package com.example.calamitycall.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.calamitycall.MainActivity;
import com.example.calamitycall.R;

public class BoardsPage extends Fragment {

    private static final String CHANNEL_ID = "channel1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_board_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up notification buttons
        view.findViewById(R.id.criticalTrigger).setOnClickListener(v -> notificationButtonCritical(v));
        view.findViewById(R.id.urgentTrigger).setOnClickListener(v -> notificationButtonUrgent(v));
        view.findViewById(R.id.warningTrigger).setOnClickListener(v -> notificationButtonWarning(v));
        view.findViewById(R.id.watchTrigger).setOnClickListener(v -> notificationButtonWatch(v));

        // Create notification channel
        createNotificationChannel();
    }

    // Trigger notifications
    public void notificationButtonCritical(View view) {
        sendNotification("Critical Alert", "Tornado Spotted", R.layout.basic_notif_critical_collapsed, R.layout.basic_notif_critical_expanded, 1);
    }

    public void notificationButtonUrgent(View view) {
        sendNotification("Urgent Alert", "High Chance of Tornado", R.layout.basic_notif_urgent_collapsed, R.layout.basic_notif_urgent_expanded, 2);
    }

    public void notificationButtonWarning(View view) {
        sendNotification("Warning Alert", "Medium Chance of Tornado", R.layout.basic_notif_warning_collapsed, R.layout.basic_notif_warning_expanded, 3);
    }

    public void notificationButtonWatch(View view) {
        sendNotification("Watch Alert", "Low Chance of Tornado", R.layout.basic_notif_watch_collapsed, R.layout.basic_notif_watch_expanded, 4);
    }

    // Helper method to create and send notifications
    private void sendNotification(String level, String type, int collapsedLayoutId, int expandedLayoutId, int notificationId) {
        // Collapsed layout for the push notification
        RemoteViews collapsedLayout = new RemoteViews(getActivity().getPackageName(), collapsedLayoutId);
        collapsedLayout.setTextViewText(R.id.disaster_level, level);
        collapsedLayout.setTextViewText(R.id.disaster_type, type);

        // Expanded layout for the push notification
        RemoteViews expandedLayout = new RemoteViews(getActivity().getPackageName(), expandedLayoutId);
        expandedLayout.setTextViewText(R.id.disaster_level, level);
        expandedLayout.setTextViewText(R.id.disaster_type, type);
        expandedLayout.setTextViewText(R.id.notification_details, "Location: Kitchener\nSent From: Emergency Services\nLatitude: 43.4516\nLongitude: 43.4516");

        Intent activityCancelIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent cancelContentIntent = PendingIntent.getActivity(getActivity(), 0, activityCancelIntent, PendingIntent.FLAG_IMMUTABLE);

        expandedLayout.setOnClickPendingIntent(R.id.action_button, cancelContentIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setCustomContentView(collapsedLayout)
                .setCustomBigContentView(expandedLayout)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(notificationId, builder.build());
        }
    }

    // Create the notification channel for Android 8.0 and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Disaster Alerts";
            String description = "Channel for disaster alert notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
