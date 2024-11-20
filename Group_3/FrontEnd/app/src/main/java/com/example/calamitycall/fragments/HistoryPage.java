package com.example.calamitycall.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calamitycall.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.material.tabs.TabLayout;  // required for the tab functionality
import com.google.android.material.textfield.TextInputLayout;

public class HistoryPage extends Fragment {
    private NotificationAdapter adapter;
    private List<Notification> activeNotifications;
    private List<Notification> historyNotifications;


    // The following three lines are related to the dropdown on the history tab
    String [] dropdownItems  = {"1 week ago", "1 month ago", "3 months ago", "6 months ago"};
    AutoCompleteTextView timeframeDropdown;
    ArrayAdapter<String> adapterItems;
    TextInputLayout dropdownLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.notif_active_history_page, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize notifications lists for each tab
        initializeNotificationLists();


        // Initialize the dropdown (AutoCompleteTextView) and set adapter for it
        dropdownLayout = view.findViewById(R.id.timeFrameDropdownLayout); // TextInputLayout
        timeframeDropdown = view.findViewById(R.id.timeFrameDropdown);
        adapterItems = new ArrayAdapter<>(requireContext(), R.layout.timeframe_list_items, dropdownItems);
        timeframeDropdown.setAdapter(adapterItems);

        timeframeDropdown.setOnItemClickListener((adapterView, view1, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
        });

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
                        dropdownLayout.setVisibility(View.GONE); // Hide dropdown
                        Log.d("TabSelection", "Active tab selected, dropdown hidden");
                        break;
                    case 1: // History Tab
                        adapter.updateNotifications(historyNotifications, true);
                        dropdownLayout.setVisibility(View.VISIBLE); // Show dropdown
                        Log.d("TabSelection", "History tab selected, dropdown shown");
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

        // Create a list of dummy dates
        List<Date> dummyDates = new ArrayList<>();


        // Define the date format
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Adding dummy dates to simulate database values
            dummyDates.add(dateFormat.parse("21-11-2023"));
            dummyDates.add(dateFormat.parse("22-11-2023"));

        } catch (ParseException e) {
            //e.printStackTrace();
            // In case of parsing issues, the current date will be added
            dummyDates.add(new Date());
        }


        // Active notifications
        activeNotifications = new ArrayList<>();

        Notification notification1 = new Notification();
        notification1.ActiveNoitificationSetter("Rainfall", 3);
        activeNotifications.add(notification1);

        Notification notification2 = new Notification();
        notification2.ActiveNoitificationSetter("Tornado", 2);
        activeNotifications.add(notification2);






        // History notifications
        historyNotifications = new ArrayList<>();

        // Creating Notification objects and setting their dates using the dummy dates list
        Notification notification3 = new Notification();
        notification3.HistoryNotificationSetter("Rainfall", 4, dummyDates.get(0));
        historyNotifications.add(notification3);

        Notification notification4 = new Notification();
        notification4.HistoryNotificationSetter("Tornado", 1, dummyDates.get(1));
        historyNotifications.add(notification4);
    }



}
