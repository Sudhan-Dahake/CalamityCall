package com.example.calamitycall;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        Spinner spinner = findViewById(R.id.spinner_incident_type);
        Spinner spinner1 = findViewById(R.id.spinner_incident_severity);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(ReportActivity.this, "Selected Incident", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        // Todo: Read from the file instead
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Avalanche");
        arrayList.add("Blizzard");
        arrayList.add("Chemical Spill");
        arrayList.add("Earthquake");
        arrayList.add("Flood");
        arrayList.add("Fog");
        arrayList.add("Freezing Rain");
        arrayList.add("Hailstorm");
        arrayList.add("Heat Wave");
        arrayList.add("Hurricane");
        arrayList.add("Landslide");
        arrayList.add("Nuclear Accident");
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

        ArrayAdapter<String> Incidentadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        Incidentadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(Incidentadapter);

        // Array for severity levels
        ArrayList<String> severityLevels = new ArrayList<>();
        severityLevels.add("Moderate");
        severityLevels.add("Severe");
        severityLevels.add("Extreme");

        // Adapter for severity levels
        ArrayAdapter<String> severityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, severityLevels);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(severityAdapter);

    }
}
