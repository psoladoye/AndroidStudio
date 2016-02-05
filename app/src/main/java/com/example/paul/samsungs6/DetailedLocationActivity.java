package com.example.paul.samsungs6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailedLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    enum DayOfWeek {
        SUNDAY("Sunday"), MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday");

        String day;

        DayOfWeek(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }
    }

    private GoogleMap mMap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapId);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Location selectedLocation = (Location) intent
                .getSerializableExtra(LocationsActivity.SELECTED_LOCATION);

        toolbar.setTitle(selectedLocation.getTitle());
        setSupportActionBar(toolbar);

        ImageView locationImage = (ImageView) findViewById(R.id.locationImage);
        Picasso.with(DetailedLocationActivity.this).load(selectedLocation.getImageUrl())
                .into(locationImage);

        TextView description = (TextView) findViewById(R.id.card_image_description);
        description.setText(selectedLocation.getDescription());
        TextView webLink = (TextView) findViewById(R.id.web_link);
        webLink.setText(String.format("%-12s%-12s", "Website", selectedLocation.getUrl()));
        TextView operatingHours = (TextView) findViewById(R.id.operating_hours);

        operatingHours.setText(
                String.format("%-20s%18s\n", DayOfWeek.SUNDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.SUNDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.MONDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.MONDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.TUESDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.TUESDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.WEDNESDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.WEDNESDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.THURSDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.THURSDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.FRIDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.FRIDAY.getDay())) +
                        String.format("%-20s%18s\n", DayOfWeek.SATURDAY.getDay(), selectedLocation.getOperatingHours().get(DayOfWeek.SATURDAY.getDay()))
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMap.setMyLocationEnabled(true);

        Intent intent = getIntent();
        Location selectedLocation = (Location) intent
                .getSerializableExtra(LocationsActivity.SELECTED_LOCATION);

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(selectedLocation.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList.isEmpty()) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        } else {
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng)).setTitle(selectedLocation.getTitle());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            CameraUpdate position=
                    CameraUpdateFactory.newLatLng(latLng);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
            mMap.moveCamera(position);
            mMap.animateCamera(zoom);
        }

    }

}
