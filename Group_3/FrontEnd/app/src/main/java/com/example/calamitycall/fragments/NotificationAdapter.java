package com.example.calamitycall.fragments;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calamitycall.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private boolean isHistoryTab;

    public NotificationAdapter(List<Notification> notifications, boolean isHistoryTab) {
        this.notifications = notifications;
        this.isHistoryTab = isHistoryTab;
    }


    // Method to update notifications list and refresh RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    public void updateNotifications(List<Notification> newNotifications, boolean isHistoryTab) {
        notifications = newNotifications;
        this.isHistoryTab = isHistoryTab;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        // First determine what tab the user is on and based on that display the correct layout

        if (isHistoryTab) {
            switch (viewType) { // History Tab layouts
                case 1: // Critical
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_critical, parent, false);
                    break;
                case 2: // Urgent in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_urgent, parent, false);
                    break;
                case 3: // Warning in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_warning, parent, false);
                    break;
                case 4: // Watch in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_watch, parent, false);
                    break;
                default:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_watch, parent, false);
                    break;
            }
        }
        else { // Active Tab layouts
            switch (viewType) {
                case 1: // Critical in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_critial, parent, false);
                    break;
                case 2: // Urgent in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_urgent, parent, false);
                    break;
                case 3: // Warning in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_warning, parent, false);
                    break;
                case 4: // Watch in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_watch, parent, false);
                    break;
                default:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_watch, parent, false);
                    break;
                }
            }


        return new NotificationViewHolder(view);
        }


        @Override
        public void onBindViewHolder (@NonNull NotificationViewHolder holder,int position){
            Notification notification = notifications.get(position);

            // Set dynamic text for disaster level
            // This will enable the text for each alert to be changed dynamically based on the disaster type
            String levelText;
            String disasterTypeText;
            String notifDate;

            switch (notification.getLevel()) {
                case 4:
                    levelText = "Critical Alert";
                    disasterTypeText = notification.getDisasterType() + " Spotted";
                    notifDate = notification.getDate();
                    break;

                case 3:
                    levelText = "Urgent Alert";
                    disasterTypeText = "High Chance of " + notification.getDisasterType();
                    notifDate = notification.getDate();
                    break;

                case 2:
                    levelText = "Warning Alert";
                    disasterTypeText = "Medium Chance of " + notification.getDisasterType();
                    notifDate = notification.getDate();
                    break;

                case 1:
                    levelText = "Watch Alert";
                    disasterTypeText = "Low Chance of " + notification.getDisasterType();
                    notifDate = notification.getDate();
                    break;

                default:
                    levelText = "Alert";
                    disasterTypeText = notification.getDisasterType();
                    notifDate = "00/00/0000";
                    break;
            }

            // Set the text on the views
            holder.disasterLevel.setText(levelText);
            holder.disasterType.setText(disasterTypeText);



            if (holder.alertDate != null) {
                Log.e("NotificationAdapter", "alertDate is NOT null!");
                holder.alertDate.setText(notifDate);
            } else {
                Log.e("NotificationAdapter", "alertDate is null!");
            }


        }


        @Override
        public int getItemViewType (int position){
            Notification notification = notifications.get(position);
            switch (notification.getLevel()) {
                case 4: // Critical
                    return 1;
                case 3: // Urgent
                    return 2;
                case 2: // Warning
                    return 3;

                case 1: // Watch
                    return 4;
                default:
                    return 0; // Default type
            }
        }


        @Override
        public int getItemCount () {
            return notifications.size();
        }

        public static class NotificationViewHolder extends RecyclerView.ViewHolder {
            TextView disasterLevel;
            TextView disasterType;
            TextView alertDate;
            View notificationLayout;

            public NotificationViewHolder(@NonNull View itemView) {
                super(itemView);
                disasterLevel = itemView.findViewById(R.id.disaster_level);
                disasterType = itemView.findViewById(R.id.disaster_type);
                alertDate = itemView.findViewById(R.id.alert_date);
                notificationLayout = itemView.findViewById(R.id.notification_layout);
            }
        }
    }
