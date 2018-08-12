package com.example.jordan.familymap.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jordan.familymap.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String eventID;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventID= null;
            } else {
                eventID = extras.getString("currentEvent");
            }
        } else {
            eventID = (String) savedInstanceState.getSerializable("currentEvent");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_event);

        if (fragment == null) {
            Bundle bundle= new Bundle();
            bundle.putString("eventID", eventID);
            fragment = new MapFrag();
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.fragment_container_event, fragment)
                    .commit();
        }
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
