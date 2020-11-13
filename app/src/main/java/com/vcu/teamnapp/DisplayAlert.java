package com.vcu.teamnapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class DisplayAlert extends AppCompatActivity implements OnMapReadyCallback {

    public static DisplayAlert Instance;
    public static GoogleMap mMap;
    private String vcuAlert = "";
    private String[] coordinates = new String[2];
    public static HashMap<LatLng, Marker> markerList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alert);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(vcuAlert != null)
            mapFragment.getMapAsync(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public void openSettingsActivity(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        Intent myIntent = new Intent(this, com.vcu.teamnapp.SettingsActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        setmMap(googleMap);
        if(!vcuAlert.equals("")){
            Geocoder geocoder = new Geocoder();
            AsyncTask task = geocoder.execute(vcuAlert);
            try {
                coordinates = (String[]) task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            double lat = Double.parseDouble(coordinates[0]);
            double lon = Double.parseDouble(coordinates[1]);
            //place a marker on our map
            LatLng alert = new LatLng(lat, lon);
            if(markerList.containsKey(alert)){
                markerList.get(alert).remove();
                markerList.clear();
            }
            else{
                Marker amarker = mMap.addMarker(new MarkerOptions().position(alert).title("Vcu alert"));
                markerList.put(alert, amarker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(alert));
            }
        }

    }
    public void setVcuAlert(String alert){
        vcuAlert = alert;
    }
    public void setmMap(GoogleMap map){
        if(map != null){
            mMap = map;
        }
    }
    public void placeAlert(){
        if (mMap != null) onMapReady(mMap);
    }
    public void setInstance(){
        Instance = this;
    }
}

