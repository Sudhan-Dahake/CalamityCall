package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.calamitycall.Notif_Management.*;
import com.example.calamitycall.R;

public class NotificationPage extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_notification_page, container, false);
        NotificationManager notifManager = new NotificationManager(RetrofitInstance.getRetrofitInstance().create(ApiService.class));

        notifManager.getNotificationHistory("1 month ago", view);

        return view;
    }
}