package com.example.calamitycall.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calamitycall.R;
import com.example.calamitycall.Settings;

public class SettingsPage extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the Settings activity
        Intent intent = new Intent(getActivity(), Settings.class);
        startActivity(intent);

        // Optionally finish this fragment's host activity if you don’t want to return to it
        requireActivity().finish();

        // Return null as this fragment is simply used to start the activity
        return null;
    }
}