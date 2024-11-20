package com.example.calamitycall.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calamitycall.FlashingActivity;
import com.example.calamitycall.NoiseActivity;
import com.example.calamitycall.NotificationOnActivity;
import com.example.calamitycall.NotificationTypeActivity;
import com.example.calamitycall.R;
import com.example.calamitycall.TexttospeechActivity;

public class ReportPage extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_report_page, container, false);
    }

    // Helper method to open an activity safely
    private void openActivity(Class<?> activityClass) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), activityClass);
            startActivity(intent);
        }
    }
}
