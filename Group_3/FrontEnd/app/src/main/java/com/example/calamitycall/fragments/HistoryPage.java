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

public class HistoryPage extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notif_active_history_page, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create or fetch your list of notifications
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Tornado", 2));
        notifications.add(new Notification("Rainfall", 3));
        notifications.add(new Notification("Rainfall", 4));
        notifications.add(new Notification("Tornado", 1));

        // Set up the adapter
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
