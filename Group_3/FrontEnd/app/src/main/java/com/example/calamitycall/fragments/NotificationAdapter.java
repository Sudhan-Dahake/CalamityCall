package com.example.calamitycall.fragments;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

                case 1: // Watch in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_watch, parent, false);
//                    Log.d("NotificationAdapter", "Inflating HISTORY WATCH layout.");
                    break;

                case 2: // Warning in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_warning, parent, false);
//                    Log.d("NotificationAdapter", "!!!!! Inflating HISTORY WARNING layout.");
                    break;

                case 3: // Urgent in History
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_urgent, parent, false);
                    break;

                case 4: // Critical
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_critical, parent, false);
//                    Log.d("NotificationAdapter", "Inflating HISTORY CRITICAL layout.");
                    break;
                default:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_notif_watch, parent, false);
                    break;
            }
        }
        else { // Active Tab layouts
            switch (viewType) {
                case 1: // Watch in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_watch, parent, false);
//                    Log.d("NotificationAdapter", "Inflating ACTIVE WATCH layout.");

                    break;

                case 2: // Warning in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_warning, parent, false);
//                    Log.d("NotificationAdapter", "Inflating ACTIVE WARNING layout.");
                    break;

                case 3: // Urgent in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_urgent, parent, false);
//                    Log.d("NotificationAdapter", "Inflating ACTIVE URGENT layout.");
                    break;

                case 4: // Critical in Active
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_critial, parent, false);
//                    Log.d("NotificationAdapter", "Inflating ACTIVE CRITICAL layout.");
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

//            Log.d("onBindViewHolder", " onBindViewHolder  Notification at position " + position + ": level=" + notification.getLevel() + ", date=" + notification.getDate());

            // Set dynamic text for disaster level
            // This will enable the text for each alert to be changed dynamically based on the disaster type
            String levelText;
            String disasterTypeText;
            String notifDate;
            String notifTime;

            notifDate = notification.getDate();
            notifTime = notification.getTime();

            switch (notification.getLevel()) {
                case 1:
                    levelText = "Watch Alert";
                    disasterTypeText = "Low Chance of " + notification.getDisasterType();
                    break;

                case 2:
                    levelText = "Warning Alert";
                    disasterTypeText = "Medium Chance of " + notification.getDisasterType();
                    break;

                case 3:
                    levelText = "Urgent Alert";
                    disasterTypeText = "High Chance of " + notification.getDisasterType();
                    break;

                case 4:
                    levelText = "Critical Alert";
                    disasterTypeText = notification.getDisasterType() + " Spotted";
                    break;

                default:
                    levelText = "Alert";
                    disasterTypeText = notification.getDisasterType();
                    notifDate = "00/00/0000";
                    notifTime = "00:00 AM";
                    break;
            }

            // Set the text on the views
            holder.disasterLevel.setText(levelText);
            holder.disasterType.setText(disasterTypeText);
//            Log.d("NotificationAdapter", "disasterTypeText: " + disasterTypeText);
//            Log.d("NotificationAdapter", "notifDate: " + notifDate);


            if (holder.alertDate != null) {
                Log.d("NotificationAdapter", "alertDate is NOT null!");
                holder.alertDate.setText(notifDate);
            } else {
                Log.e("NotificationAdapter", "alertDate is null!");
            }

            if (holder.alertTime != null) {
                Log.d("NotificationAdapter", "alertTime is NOT null!");
                holder.alertTime.setText(notifTime);
            } else {
                Log.e("NotificationAdapter", "alertTime is null!");
            }


        }


        @Override
        public int getItemViewType (int position){
            Notification notification = notifications.get(position);

//            Log.d("getItemViewType", " getItemViewType  Notification at position " + position + ": level=" + notification.getLevel() + ", date=" + notification.getDate());


            switch (notification.getLevel()) {
                case 1: // Watch
                    return 1;
                case 2: // Warning
//                    Log.e("getItemViewType", "THIS IS THE VIEW TYPE FOR WARNING");
                    return 2;
                case 3: // Urgent
                    return 3;
                case 4: // Critical
//                    Log.e("getItemViewType", "THIS IS THE VIEW TYPE FOR CRITICAL");
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
            TextView alertTime;
            View notificationLayout;

            public NotificationViewHolder(@NonNull View itemView) {
                super(itemView);
                disasterLevel = itemView.findViewById(R.id.disaster_level);
                disasterType = itemView.findViewById(R.id.disaster_type);
                alertDate = itemView.findViewById(R.id.alert_date);
                alertTime = itemView.findViewById(R.id.alert_time);
                if (alertDate == null) {
                    Log.e("NotificationViewHolder", "alertDate is null in layout: " + itemView.getId());
                }
                if(alertTime == null){
                    Log.e("NotificationViewHolder", "alertTime is null in layout: " + itemView.getId());
                }

                notificationLayout = itemView.findViewById(R.id.notification_layout);
            }
        }
    }
