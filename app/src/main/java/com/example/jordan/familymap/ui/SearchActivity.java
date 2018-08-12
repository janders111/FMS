package com.example.jordan.familymap.ui;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;

import java.util.ArrayList;

import model.Event;
import model.Person;

//extends AppCompatActivity, ListActivity
public class SearchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_search);

        SearchView searchView = (SearchView) findViewById(R.id.mySearchView);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setContentView(R.layout.activity_search);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SearchAdapter(getNarrowedResults("sh"), getBaseContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Object> getNarrowedResults(String search) {
        search = search.toLowerCase();
        ArrayList<Object> results = new ArrayList<>();
        for(Person p : MainModel.getPeople()) {
            if(p.getLastName().contains(search) || p.getFirstName().contains(search)) {
                results.add(p);
            }
        }
        for(Event e : MainModel.getEvents()) {
            if(e.getEventType().contains(search)) {
                results.add(e);
            }
        }
        for(int i = 0; i < 8; i++) {
            results.add(MainModel.getPeople()[i]);
        }
        for(int i = 0; i < 13; i++) {
            results.add(MainModel.getEvents()[i]);
        }
        return results;
    }
}
