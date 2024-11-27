package com.example.calamitycall.fragments;
import com.example.calamitycall.R;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ReportPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_report_page, container, false);

        CheckBox checkboxShareLocation = view.findViewById(R.id.checkbox_share_location);
        TextView textShareLocation = view.findViewById(R.id.text_share_location);

        // Listener for CheckBox and TextView
        View.OnClickListener locationClickListener = v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // Permission already granted
                checkboxShareLocation.setChecked(true);
                Toast.makeText(getContext(), "Location permission granted", Toast.LENGTH_SHORT).show();
            }
        };
        // Set listener on both the CheckBox and TextView
        checkboxShareLocation.setOnClickListener(locationClickListener);
        textShareLocation.setOnClickListener(locationClickListener);

        // Spinner for incident types
        Spinner spinner = view.findViewById(R.id.spinner_incident_type);
        Spinner spinner1 = view.findViewById(R.id.spinner_incident_severity);

        // Set onItemSelectedListener for incident type spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(getContext(), "Selected Incident: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Incident types list
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Avalanche");
        arrayList.add("Blizzard");
        arrayList.add("Earthquakes");
        arrayList.add("Flooding");
        arrayList.add("Fog");
        arrayList.add("Freezing rain");
        arrayList.add("Hailstorm");
        arrayList.add("Heat Wave");
        arrayList.add("Hurricane");
        arrayList.add("Landslide");
        arrayList.add("Power Outage");
        arrayList.add("Sandstorm");
        arrayList.add("Sinkhole");
        arrayList.add("Snowstorm");
        arrayList.add("Thunderstorm");
        arrayList.add("Tornado");
        arrayList.add("Tropical Storm");
        arrayList.add("Tsunami");
        arrayList.add("Volcano Eruption");
        arrayList.add("Wildfire");

        // Adapter for incident types
        ArrayAdapter<String> incidentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        incidentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(incidentAdapter);

        // Severity levels list
        ArrayList<String> severityLevels = new ArrayList<>();
        severityLevels.add("Watch");
        severityLevels.add("Warning");
        severityLevels.add("Critical");
        severityLevels.add("Urgent");

        // Adapter for severity levels
        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, severityLevels);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(severityAdapter);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        CheckBox checkboxShareLocation = getView().findViewById(R.id.checkbox_share_location);

        checkboxShareLocation.setOnClickListener(v -> {
            if (checkboxShareLocation.isChecked()) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission already granted
                    Toast.makeText(getContext(), "Location permission already granted", Toast.LENGTH_SHORT).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show permission dialog again
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    // Permission permanently denied; guide user to app settings
                    checkboxShareLocation.setChecked(false); // Uncheck the box since permission is still denied
                    showPermissionSettingsDialog();
                }
            }
        });

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                checkboxShareLocation.setChecked(true);
                Toast.makeText(getContext(), "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                checkboxShareLocation.setChecked(false);
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPermissionSettingsDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Location Permission Required")
                .setMessage("Location access is needed to share your location. Please enable it in the app settings.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    // Open app settings
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Close dialog
                    dialog.dismiss();
                })
                .show();
    }
}

