package com.vcu.teamnapp;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vcu.geocoder.Geocoder;

import java.util.concurrent.ExecutionException;

public class DisplayAlert extends FragmentActivity implements OnMapReadyCallback {

    public static DisplayAlert Instance;
    private GoogleMap mMap;
    private String vcuAlert;
    private String[] coordinates = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alert);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(vcuAlert != null && mMap != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        setmMap(googleMap);
        Geocoder geoCoderNotCalled = new Geocoder();
        AsyncTask task = Geocoder.geocoder.execute(vcuAlert);
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
        mMap.addMarker(new MarkerOptions().position(alert).title("Vcu alert marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alert));
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