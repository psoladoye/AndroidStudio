package com.example.paul.samsungs6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

public class LocationsActivity extends AppCompatActivity {
    ListView listView;
    LocationAdapter locationAdapter;
    Gson gson;
    Location[] locations;
    public final static String SELECTED_LOCATION = "selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.locationList);

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra(MainActivity.LOCATIONS);

        gson = new Gson();
        locations = gson.fromJson(jsonString, Location[].class);
        locationAdapter = new LocationAdapter(this,locations);

        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocationsActivity.this, DetailedLocationActivity.class);
                intent.putExtra(SELECTED_LOCATION, locations[position]);
                startActivity(intent);
            }
        });
    }




}
