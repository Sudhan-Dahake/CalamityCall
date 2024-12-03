package com.example.calamitycall.fragments;

import com.example.calamitycall.DisasterReport;
import com.example.calamitycall.R;
import com.example.calamitycall.network.ApiClient;
import com.example.calamitycall.network.DisasterRetrofitInstance;
import com.example.calamitycall.network.RetrofitInstance;

import androidx.annotation.NonNull;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                disasterReport.setEvent(incidentTypes.get(position),null,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Severity spinner setup
        ArrayList<String> severityLevels = new ArrayList<>();
        severityLevels.add("Severe");
        severityLevels.add("Warning");
        severityLevels.add("Critical");
        severityLevels.add("Urgent");

        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, severityLevels);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncidentSeverity.setAdapter(severityAdapter);
        spinnerIncidentSeverity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DisasterReport.Event currentEvent = disasterReport.getEvent();
                disasterReport.setEvent(
                        currentEvent != null ? currentEvent.getType() : null,
                        severityLevels.get(position),
                        currentEvent != null ? currentEvent.getDescription() : null
                );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Submit button click listener
        btnSubmitReport.setOnClickListener(v -> {
            String description = editDescription.getText().toString().trim();

            // Generate a unique report ID using UUID
            String reportId = UUID.randomUUID().toString();
            disasterReport.setReport_id(reportId);

            // Set user ID
            int userId = (int) (System.currentTimeMillis() % 100000);
            String formattedUserId = String.format("%05d", userId);
            disasterReport.setUser_id(Integer.parseInt(formattedUserId));

            // Set created_at timestamp
            disasterReport.setCreated_at(ZonedDateTime.now(ZoneOffset.UTC).toString());

            // Set event details from spinners
            disasterReport.setEvent(
                    spinnerIncidentType.getSelectedItem().toString(),
                    spinnerIncidentSeverity.getSelectedItem().toString(),
                    description
            );

            // Optional: Set contact if needed
            // disasterReport.setContact(editContact.getText().toString());

            try {
                JSONObject jsonReport = disasterReport.toJson();
                sendToServer(jsonReport);
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error creating report", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void captureLocation() {
        // Initialize location if it's null
        if (disasterReport.getLocation() == null) {
            disasterReport.setLocation(new DisasterReport.Location());
        }

        // Fetch and set location data (latitude and longitude)
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(getContext().LOCATION_SERVICE);
        if (locationManager != null && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Set latitude and longitude in disasterReport
                disasterReport.setLocation(latitude, longitude, null);

                // Fetch the address using Geocoder
                Geocoder geocoder = new Geocoder(getContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        String address = addresses.get(0).getAddressLine(0); // Full address
                        disasterReport.setLocation(latitude, longitude, address);
                        Toast.makeText(getContext(), "Location captured: " + address, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Unable to fetch address", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Error fetching address: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void sendToServer(JSONObject jsonReport) {
        ApiClient apiClient = DisasterRetrofitInstance.getDisasterRetrofitInstance().create(ApiClient.class);
        Call<Void> call = apiClient.submitReport(disasterReport);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Report submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "Error submitting report: " + response.code();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Network error: " + t.getMessage();
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
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
