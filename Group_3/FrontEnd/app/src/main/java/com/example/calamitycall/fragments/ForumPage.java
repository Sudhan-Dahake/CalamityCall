package com.example.calamitycall.fragments;

import com.example.calamitycall.utils.NotificationConfig;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.calamitycall.R;

public class ForumPage extends Fragment {
    private NotificationConfig notificationConfig;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_forum_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationConfig = new NotificationConfig(requireContext());
        // Set up notification buttons
        view.findViewById(R.id.criticalTrigger).setOnClickListener(this::notificationButtonCritical);
        view.findViewById(R.id.urgentTrigger).setOnClickListener(this::notificationButtonUrgent);
        view.findViewById(R.id.warningTrigger).setOnClickListener(this::notificationButtonWarning);
        view.findViewById(R.id.watchTrigger).setOnClickListener(this::notificationButtonWatch);
}

    // Trigger notifications
    public void notificationButtonCritical(View view) {
        notificationConfig.sendNotification(
                4, "Tornado Spotted", "Kitchener", "Emergency Services",
                12.432341F, 11.4321F, "Leave area immediately", "Get inside and go to the basement", "Recover any missing parts, call for recovery agencies");
    }

    public void notificationButtonUrgent(View view) {
        notificationConfig.sendNotification(
                3, "High Chance of Tornado", "Waterloo", "National Weather System",
                12.432341F, 11.4321F, null, null, null);
    }

    public void notificationButtonWarning(View view) {
        notificationConfig.sendNotification(
                2, "Medium Chance of Tornado", "Cambridge", "National Weather System",
                12.432341F, 11.4321F, null, null, null);
    }

    public void notificationButtonWatch(View view) {
        notificationConfig.sendNotification(
                1, "Low Chance of Tornado", "Guelph", "National Weather System",
                12.432341F, 11.4321F, null, null, null);
    }
}
