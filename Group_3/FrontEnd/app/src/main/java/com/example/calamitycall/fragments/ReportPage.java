package com.example.calamitycall.fragments;

import com.example.calamitycall.DisasterReport;
import com.example.calamitycall.R;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class ReportPage extends Fragment {

    private DisasterReport disasterReport = new DisasterReport();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_report_page, container, false);

        CheckBox checkboxShareLocation = view.findViewById(R.id.checkbox_share_location);
        TextView textShareLocation = view.findViewById(R.id.text_share_location);
        Spinner spinnerIncidentType = view.findViewById(R.id.spinner_incident_type);
        Spinner spinnerIncidentSeverity = view.findViewById(R.id.spinner_incident_severity);
        EditText editDescription = view.findViewById(R.id.edit_description);
        EditText editContact = view.findViewById(R.id.edit_contact);
        Button btnSubmitReport = view.findViewById(R.id.btn_submit_report);

        // Location permission handler
        View.OnClickListener locationClickListener = v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                checkboxShareLocation.setChecked(true);
                captureLocation();
            }
        };

        checkboxShareLocation.setOnClickListener(locationClickListener);
        textShareLocation.setOnClickListener(locationClickListener);

        // Incident type spinner setup
        ArrayList<String> incidentTypes = new ArrayList<>();
        incidentTypes.add("Avalanche");
        incidentTypes.add("Blizzard");
        incidentTypes.add("Earthquake");
        incidentTypes.add("Flooding");
        incidentTypes.add("Fog");
        incidentTypes.add("Freezing rain");
        incidentTypes.add("Hailstorm");
        incidentTypes.add("Heat Wave");
        incidentTypes.add("Hurricane");
        incidentTypes.add("Landslide");
        incidentTypes.add("Power Outage");
        incidentTypes.add("Sandstorm");
        incidentTypes.add("Sinkhole");
        incidentTypes.add("Snowstorm");
        incidentTypes.add("Thunderstorm");
        incidentTypes.add("Tornado");
        incidentTypes.add("Tropical Storm");
        incidentTypes.add("Tsunami");
        incidentTypes.add("Volcano Eruption");
        incidentTypes.add("Wildfire");

        ArrayAdapter<String> incidentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, incidentTypes);
        incidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncidentType.setAdapter(incidentAdapter);
        spinnerIncidentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disasterReport.setEventType(incidentTypes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Severity spinner setup
        ArrayList<String> severityLevels = new ArrayList<>();
        severityLevels.add("Watch");
        severityLevels.add("Warning");
        severityLevels.add("Critical");
        severityLevels.add("Urgent");

        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, severityLevels);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncidentSeverity.setAdapter(severityAdapter);
        spinnerIncidentSeverity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disasterReport.setSeverity(severityLevels.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Submit button click listener
        btnSubmitReport.setOnClickListener(v -> {
            String description = editDescription.getText().toString().trim();
            String contact = editContact.getText().toString().trim();
            disasterReport.setDescription(description);
            disasterReport.setContact(contact);
            disasterReport.setUserId(UUID.randomUUID().toString());
            disasterReport.setReportId(UUID.randomUUID().toString());
            disasterReport.setCreatedAt(java.time.ZonedDateTime.now().toString());

            try {
                JSONObject jsonReport = disasterReport.toJson();
                sendToServer(jsonReport);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error creating JSON object", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void captureLocation() {
        // Fetch and set location data (latitude and longitude)
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(getContext().LOCATION_SERVICE);
        if (locationManager != null && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                disasterReport.setLocation(location.getLatitude(), location.getLongitude());
                Toast.makeText(getContext(), "Location captured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendToServer(JSONObject jsonReport) {
        // Simulated server call
        Toast.makeText(getContext(), "Report submitted: " + jsonReport.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureLocation();
        } else {
            Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
