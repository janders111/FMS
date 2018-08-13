package com.example.jordan.familymap.ui;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.FilterManager;
import com.example.jordan.familymap.model.MainModel;

import ProxyServer.ProxyServer;
import ReqAndResponses.LoginRequest;

public class FiltersActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Switch showMales;
    private Switch showFemales;
    private Switch showPaternal;
    private Switch showMaternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FilterManager.init();
        setContentView(R.layout.activity_filters);
        mRecyclerView = (RecyclerView) findViewById(R.id.filterRecycler);
        showMales = findViewById(R.id.malesSwitch);
        showMales.setChecked(FilterManager.getShowMales());
        showMales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterManager.setShowMales(isChecked);
            }
        });

        showFemales = findViewById(R.id.femalesSwitch);
        showFemales.setChecked(FilterManager.getShowFemales());
        showFemales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterManager.setShowFemales(isChecked);
            }
        });

        showPaternal = findViewById(R.id.fathersSideSwitch);
        showPaternal.setChecked(FilterManager.getShowPaternal());
        showPaternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterManager.setShowPaternal(isChecked);
            }
        });

        showMaternal = findViewById(R.id.mothersSideSwitch);
        showMaternal.setChecked(FilterManager.getShowMaternal());
        showMaternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FilterManager.setShowMaternal(isChecked);
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FiltersAdapter(MainModel.getEventTypes());
        mRecyclerView.setAdapter(mAdapter);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
}