package com.example.calamitycall.fragments;
import com.example.calamitycall.R;

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
}
