package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calamitycall.R;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.example.calamitycall.models.NotificationHistory.NotificationHistoryRequest;
import com.example.calamitycall.models.NotificationHistory.NotificationHistoryResponse;
import com.example.calamitycall.models.NotificationHistory.NotificationResponse;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.RetrofitInstance;
import com.example.calamitycall.utils.TokenManager;
import com.google.android.material.tabs.TabLayout;  // required for the tab functionality
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPage extends Fragment {
    private static final String TAG = "NotificationPage";
    private NotificationAdapter adapter;
    private List<Notification> activeNotifications;
    private List<Notification> historyNotifications;

    private TokenManager tokenManager;


    // The following three lines are related to the dropdown on the history tab
    String [] dropdownItems  = {"1 week ago", "1 month ago", "3 months ago", "6 months ago"};
    AutoCompleteTextView timeframeDropdown;
    ArrayAdapter<String> adapterItems;
    TextInputLayout dropdownLayout;

    TextView Last24Hours;

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
        Last24Hours = view.findViewById(R.id.ActiveLabel);

        timeframeDropdown.setOnItemClickListener((adapterView, view1, i, l) -> {
            String selectedTimeFrame = adapterView.getItemAtPosition(i).toString();
            fetchNotificationHistory(selectedTimeFrame);

            //Toast.makeText(getContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
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
                        Last24Hours.setVisibility(View.VISIBLE);
                        Log.d("TabSelection", "Active tab selected, dropdown hidden");
                        break;
                    case 1: // History Tab
                        adapter.updateNotifications(historyNotifications, true);
                        dropdownLayout.setVisibility(View.VISIBLE); // Show dropdown
                        Last24Hours.setVisibility(View.GONE);
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



    private void fetchNotificationHistory(String timeframe) {
        // Prepare request body
        NotificationHistoryRequest request = new NotificationHistoryRequest(timeframe);

        // Make the API Call
        ApiClient apiClient = RetrofitInstance.getRetrofitInstance().create(ApiClient.class);
        Call<NotificationHistoryResponse> call = apiClient.getNotificationHistory(request);

        call.enqueue(new Callback<NotificationHistoryResponse>() {
            @Override
            public void onResponse(Call<NotificationHistoryResponse> call, Response<NotificationHistoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NotificationHistoryResponse historyResponse = response.body();

                    historyNotifications.clear();

                    for (NotificationResponse notificationResponse : historyResponse.getNotifications()) {
                        Notification notification = new Notification(
                                notificationResponse.getDisastertype(),
                                notificationResponse.getDisasterlevel(),
                                notificationResponse.getNotifdate()
                        );
                        historyNotifications.add(notification);
                    }

                    // Notify the adapter of the updated data
                    adapter.updateNotifications(historyNotifications, true);
                    Log.d(TAG, "Successfully fetched and updated history notifications.");
                }

                else {
                    Log.e(TAG, "Failed to fetch notifications. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NotificationHistoryResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching notifications", t);
            }
        });
    }



    // Initialize lists for Active and History notifications
    private void initializeNotificationLists() {
        // Active notifications
        activeNotifications = new ArrayList<>();
//        activeNotifications.add(new Notification("Tornado", 2));
//        activeNotifications.add(new Notification("Rainfall", 3));

        historyNotifications = new ArrayList<>(); // Start with an empty list
    }
}