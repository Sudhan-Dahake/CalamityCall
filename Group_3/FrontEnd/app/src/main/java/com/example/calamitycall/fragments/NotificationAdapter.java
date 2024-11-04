package com.example.calamitycall.fragments;

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

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // Inflate the correct layout based on viewType
        switch (viewType) {
            case 1: // Critical
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_critial, parent, false);
                break;
            case 2: // Urgent
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_urgent, parent, false);
                break;
            case 3: // Warning
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_warning, parent, false);
                break;
            case 4: // Watch
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_notif_watch, parent, false);
                break;

            default:    // this has to be updated
                // Fallback to a default layout if necessary
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_notif_watch_collapsed, parent, false);
                break;
        }
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        // Set dynamic text for disaster level
        // This will enable the text for each alert to be changed dynamically based on the disaster type
        String levelText;
        String disasterTypeText;

        switch (notification.getLevel()) {
            case 4:
                levelText = "Critical Alert";
                disasterTypeText = notification.getDisasterType() + " Spotted";
                break;
            case 3:
                levelText = "Urgent Alert";
                disasterTypeText = "High Chance of " + notification.getDisasterType();
                break;
            case 2:
                levelText = "Warning Alert";
                disasterTypeText = "Medium Chance of " + notification.getDisasterType();
                break;
            case 1:
                levelText = "Watch Alert";
                disasterTypeText = "Low Chance of " + notification.getDisasterType();
                break;
            default:
                levelText = "Alert";
                disasterTypeText = notification.getDisasterType();
                break;
        }

        // Set the text on the views
        holder.disasterLevel.setText(levelText);
        holder.disasterType.setText(disasterTypeText);
    }


    @Override
    public int getItemViewType(int position) {
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
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView disasterLevel, disasterType;
        ImageView icon;
        View notificationLayout;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            disasterLevel = itemView.findViewById(R.id.disaster_level);
            disasterType = itemView.findViewById(R.id.disaster_type);
            notificationLayout = itemView.findViewById(R.id.notification_layout);
        }
    }

}
