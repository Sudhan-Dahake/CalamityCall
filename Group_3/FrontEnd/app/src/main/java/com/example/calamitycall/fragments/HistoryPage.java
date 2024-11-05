package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calamitycall.R;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.tabs.TabLayout;  // required for the tab functionality

public class HistoryPage extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> activeNotifications;
    private List<Notification> historyNotifications;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.notif_active_history_page, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize notifications lists for each tab
        initializeNotificationLists();


        // Set up the adapter with default values
        adapter = new NotificationAdapter(activeNotifications, false);
        recyclerView.setAdapter(adapter);

        // Set up TabLayout listener to switch content
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Active Tab
                        adapter.updateNotifications(activeNotifications, false);
                        break;
                    case 1: // History Tab
                        adapter.updateNotifications(historyNotifications, true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }


    // Initialize lists for Active and History notifications
    private void initializeNotificationLists() {
        // Active notifications
        activeNotifications = new ArrayList<>();
        activeNotifications.add(new Notification("Tornado", 2));
        activeNotifications.add(new Notification("Rainfall", 3));

        // History notifications
        historyNotifications = new ArrayList<>();
        historyNotifications.add(new Notification("Rainfall", 4));
        historyNotifications.add(new Notification("Tornado", 1));
    }
}
