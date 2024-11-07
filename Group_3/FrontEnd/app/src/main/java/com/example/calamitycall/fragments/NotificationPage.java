//package com.example.calamitycall.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.calamitycall.MainActivity;
//import com.example.calamitycall.R;
//import java.util.ArrayList;
//import java.util.List;
//import com.google.android.material.tabs.TabLayout;  // required for the tab functionality
//import com.google.android.material.textfield.TextInputLayout;
//
//public class NotificationPage extends Fragment {
//
//
//    private View view;
//    private RecyclerView recyclerView;
//    private NotificationAdapter adapter;
//    private List<Notification> activeNotifications;
//    private List<Notification> historyNotifications;
//
//
//    // The following three lines are related to the dropdown on the history tab
//    String [] dropdownItems  = {"1 week ago", "1 month ago", "3 months ago", "6 months ago"};
//    AutoCompleteTextView timeframeDropdown;
//    ArrayAdapter<String> adapterItems;
//    TextInputLayout dropdownLayout;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        view = inflater.inflate(R.layout.notif_active_history_page, container, false);
//
//        // Initialize RecyclerView
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Initialize notifications lists for each tab
//        initializeNotificationLists();
//
//
//        // Initialize the dropdown (AutoCompleteTextView) and set adapter for it
//        dropdownLayout = view.findViewById(R.id.timeFrameDropdownLayout); // TextInputLayout
//        timeframeDropdown = view.findViewById(R.id.timeFrameDropdown);
//        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.timeframe_list_items, dropdownItems);
//        timeframeDropdown.setAdapter(adapterItems);
//
//        timeframeDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//        // Set up the adapter with default values
//        adapter = new NotificationAdapter(activeNotifications, false);
//        recyclerView.setAdapter(adapter);
//
//        // Set up TabLayout listener to switch content
//        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0: // Active Tab
//                        adapter.updateNotifications(activeNotifications, false);
//                        dropdownLayout.setVisibility(View.GONE); // Hide dropdown
//                        Log.d("TabSelection", "Active tab selected, dropdown hidden");
//                        break;
//                    case 1: // History Tab
//                        adapter.updateNotifications(historyNotifications, true);
//                        dropdownLayout.setVisibility(View.VISIBLE); // Show dropdown
//                        Log.d("TabSelection", "History tab selected, dropdown shown");
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {}
//        });
//
//        return view;
//    }
//
//
//    // Initialize lists for Active and History notifications
//    private void initializeNotificationLists() {
//        // Active notifications
//
//
//        activeNotifications = new ArrayList<>();
//        activeNotifications.add(new Notification("Tornado", 2));
//        activeNotifications.add(new Notification("Rainfall", 3));
//
//        // History notifications
//        historyNotifications = new ArrayList<>();
//        historyNotifications.add(new Notification("Rainfall", 4));
//        historyNotifications.add(new Notification("Tornado", 1));
//    }
//}