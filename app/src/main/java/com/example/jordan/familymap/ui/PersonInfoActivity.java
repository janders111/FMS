package com.example.jordan.familymap.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;

import java.util.ArrayList;

import model.Event;
import model.Person;

public class PersonInfoActivity extends AppCompatActivity {
    ArrayList<Event> lifeEvents;
    ArrayList<Person> family;
    ArrayList<String> relationshipType;
    String personID;
    Person person;
    TextView firstName;
    TextView lastName;
    TextView gender;
    private RecyclerView lifeEventsRecyclerView;
    private RecyclerView.Adapter universalAdapter;
    private RecyclerView familyRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager nLayoutManager;
    private LinearLayout expandRetractFamily;
    private LinearLayout expandRetractEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                personID= null;
            } else {
                personID = extras.getString("currentPerson");
            }
        } else {
            personID = (String) savedInstanceState.getSerializable("currentPerson");
        }
        person = MainModel.getPersonIDtoPerson().get(personID);

        lifeEvents = MainModel.getPersonToEvents().get(personID);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        populateFamily();
        updateView();
    }

    protected void populateFamily() {
        family = new ArrayList<Person>();
        relationshipType = new ArrayList<String>();
        //parents, spouse and children count as family.
        for(Person potentialFamily : MainModel.getPeople()) {
            if(potentialFamily.getSpouse() != null){//add spouse
                if(potentialFamily.getSpouse().equals(personID)) {
                    family.add(potentialFamily);
                    relationshipType.add("Spouse");
                    continue;
                }
            }
            if(potentialFamily.getPersonID() != null && person.getFather() != null) {
                if (potentialFamily.getPersonID().equals(person.getFather())) {
                    family.add(potentialFamily);
                    relationshipType.add("Father");
                    continue;
                }
            }
            if(potentialFamily.getPersonID() != null && person.getMother() != null) {
                if (potentialFamily.getPersonID().equals(person.getMother())) {
                    family.add(potentialFamily);
                    relationshipType.add("Mother");
                    continue;
                }
            }
        }
        ArrayList<Person> children = MainModel.getPersonToChildren().get(personID);
        for(Person child : children) {
            family.add(child);
            relationshipType.add("Child");
        }
    }

    protected void updateView() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        gender = findViewById(R.id.gender);
        expandRetractEvents = findViewById(R.id.lifeEventsDescriptionContainer);
        expandRetractFamily = findViewById(R.id.familyDescriptionContainer);
        lifeEventsRecyclerView = (RecyclerView) findViewById(R.id.lifeEventsRecycler);
        familyRecyclerView = (RecyclerView) findViewById(R.id.familyRecycler);
        lifeEventsRecyclerView.setVisibility(View.GONE);
        familyRecyclerView.setVisibility(View.VISIBLE);
        expandRetractEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(lifeEventsRecyclerView);
            }
        });
        expandRetractFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(familyRecyclerView);
            }
        });

        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        gender.setText(person.getGender().equals("m") ? "Male" : "Female");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        lifeEventsRecyclerView.setHasFixedSize(true);
        familyRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        lifeEventsRecyclerView.setLayoutManager(mLayoutManager);
        nLayoutManager = new LinearLayoutManager(this);
        familyRecyclerView.setLayoutManager(nLayoutManager);

        // specify an adapter (see also next example)
        universalAdapter = new SearchAdapter(eventArrayConverter(lifeEvents), getBaseContext());
        lifeEventsRecyclerView.setAdapter(universalAdapter);
        universalAdapter = new PersonAdapter(family, relationshipType, getBaseContext()); //Only personAdapter can handle the relationship type.
        familyRecyclerView.setAdapter(universalAdapter);
    }

    private void toggleVisibility(View v) {
        if (v.getVisibility() == View.GONE) {
            v.setVisibility(View.VISIBLE);
        }
        else {
            v.setVisibility(View.GONE);
        }
    }

    private ArrayList<Object> eventArrayConverter(ArrayList<Event> in) {
        ArrayList<Object> out = new ArrayList<>();
        for(Event e : in) {
            out.add((Object)e);
        }
        return out;
    }
}
