package com.example.jordan.familymap.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.async.LoginTask;
import com.example.jordan.familymap.model.CurrentColor;
import com.example.jordan.familymap.model.FilterManager;
import com.example.jordan.familymap.model.LineColors;
import com.example.jordan.familymap.model.MainModel;
import com.example.jordan.familymap.model.SettingsManager;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginRequest;
import ReqAndResponses.LoginResponse;

public class SettingsActivity extends AppCompatActivity {
    static Fragment sLoginFrag;
    Context mContext;
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

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("currentPerson", currentPerson.getPersonID());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //ProcessPhoenix.triggerRebirth(getBaseContext(), intent);
                startActivity(intent);
            }
        });

        mContext = this;
        Button resync = findViewById(R.id.resync);
        resync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Refreshing Data", Toast.LENGTH_LONG).show();
                clearAllData();
                MainModel.getLf().resync();
                Toast.makeText(v.getContext(), "Data Refreshed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);;
        if(item.getItemId() == android.R.id.home) {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

    private static void clearAllData() {
        CurrentColor.clear();
        FilterManager.clear();
        LineColors.clear();
        MainModel.clear();
        SettingsManager.clear();
        MapFrag.clear();
    }
}
