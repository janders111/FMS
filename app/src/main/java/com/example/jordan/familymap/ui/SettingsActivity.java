package com.example.jordan.familymap.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.LineColors;
import com.example.jordan.familymap.model.SettingsManager;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Spinner spinnerMapType = (Spinner) findViewById(R.id.mapType);
        Spinner spinnerLSColor = (Spinner) findViewById(R.id.lifeStoryColor);
        Spinner spinnerFTColor = (Spinner) findViewById(R.id.familyTreeLinesColor);
        Spinner spinnerSLColor = (Spinner) findViewById(R.id.spouseLinesColor);
        Switch lsEnableDisable = findViewById(R.id.lifeStoryLinesOnOff);
        Switch ftEnableDisable = findViewById(R.id.familyTreeLinesOnOff);
        Switch slEnableDisable = findViewById(R.id.spouseLinesLinesOnOff);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMT = ArrayAdapter.createFromResource(this, R.array.mapOptionsArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterLS = ArrayAdapter.createFromResource(this, R.array.lineColorOptions, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterFT = ArrayAdapter.createFromResource(this, R.array.lineColorOptions, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSL = ArrayAdapter.createFromResource(this, R.array.lineColorOptions, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterMT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterFT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerMapType.setAdapter(adapterMT);
        spinnerLSColor.setAdapter(adapterLS);
        spinnerFTColor.setAdapter(adapterFT);
        spinnerSLColor.setAdapter(adapterSL);


        spinnerMapType.setSelection(SettingsManager.getCurrentMapType());
        spinnerLSColor.setSelection(LineColors.getColorLSIndex());
        spinnerFTColor.setSelection(LineColors.getColorFTIndex());
        spinnerSLColor.setSelection(LineColors.getColorSLIndex());
        lsEnableDisable.setChecked(SettingsManager.getLifeStoryLines());
        ftEnableDisable.setChecked(SettingsManager.getFamilyTreeLines());
        slEnableDisable.setChecked(SettingsManager.getSpouseLines());

        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SettingsManager.setCurrentMapType(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        spinnerLSColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineColors.setColorLS(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });

        spinnerFTColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineColors.setColorFT(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });
        spinnerSLColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LineColors.setColorSL(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  }
        });
        lsEnableDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsManager.setLifeStoryLines(isChecked);
            }
        });
        ftEnableDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsManager.setFamilyTreeLines(isChecked);
            }
        });
        slEnableDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsManager.setSpouseLines(isChecked);
            }
        });
    }
}
