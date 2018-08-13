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
import android.view.MenuItem;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.FilterManager;
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

        setContentView(R.layout.activity_search);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        SearchView searchView = (SearchView) findViewById(R.id.mySearchView);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter = new SearchAdapter(getNarrowedResults(query), getBaseContext());
                mRecyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private ArrayList<Object> getNarrowedResults(String search) {
        search = search.toLowerCase();
        ArrayList<Object> results = new ArrayList<>();
        for(Person p : MainModel.getPeople()) {
            if(p.getLastName().toLowerCase().contains(search) || p.getFirstName().toLowerCase().contains(search)) {
                results.add(p);
            }
        }
        for(Event e : FilterManager.getFilteredEvents()) {
            if(e.getEventType().toLowerCase().contains(search)) {
                results.add(e);
            }
        }
        return results;
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
