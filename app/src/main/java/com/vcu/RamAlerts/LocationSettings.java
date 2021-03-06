package com.vcu.RamAlerts;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationSettings extends AppCompatActivity {

    private static TextView myTextView;
    private static TextView myTextView2;
    private static TextView myTextView3;
    private static Switch mySwitch;
    public static boolean switchStatus;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static double latitude;
    private static double longitude;
    private static Location moLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        myTextView2 = findViewById(R.id.textView3);
        myTextView3 = findViewById(R.id.textView4);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkLocationServices(locationManager);
        locationListener();
        updateLocation();
        seekBar();
    }
    public void setLatitude(Location location){
        this.latitude = location.getLatitude();
    }
    public static double getLatitude(){
        return latitude;
    }
    public void setLongitude(Location location){
        this.longitude = location.getLongitude();
    }
    public static double getLongitude(){
        return longitude;
    }
    public boolean getSwitchStatus(){
        return mySwitch.isChecked();
    }
    public void locationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (getSwitchStatus()) {
                    moLocation = location;
                    setLatitude(moLocation);
                    setLongitude(moLocation);
                    latitude = getLatitude();
                    longitude = getLongitude();
                    DisplayAlertFragment myDisplay = new DisplayAlertFragment();
                    myDisplay.displayUserLocation();

                    //called whenever location is updated
                    myTextView2.setText("Latitude: " + latitude);
                    myTextView3.setText("Longitude: " + longitude);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                //checks if gps is turned off
                alertMessageNoGps(locationManager);

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);
                return;
            }
        }else{
            updateLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateLocation();
                } return;
        }
    }

    private void updateLocation() {
        try {
            // updates the location for every 5 seconds (5000 ms) and
            // only if the user moved at least 5 meters from the previous location
            locationManager.requestLocationUpdates("gps", 5000, 5, locationListener);
            locationListener();

        }catch (SecurityException e){
            Toast.makeText(LocationSettings.this, "Request to update location permission is rejected, please turn on location services!", Toast.LENGTH_LONG).show();
        }
    }

    public void seekBar() {
        SeekBar mySeekBar = (SeekBar) findViewById(R.id.seekBar);
        myTextView = (TextView) findViewById(R.id.myTextView);
        myTextView.setText("Distance : " + mySeekBar.getProgress() +  " mile radius");

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double progressValue = 0.0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double progressDecimal = (double) progress * 1/5;
                progressValue = progressDecimal;
                myTextView.setText("Distance:  " + progressValue + " mile radius");
                Toast.makeText(LocationSettings.this, "SeekBar is in progress", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(LocationSettings.this, "Begin tracking ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(LocationSettings.this, "End tracking ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checkLocationServices(final LocationManager locationManager){
        //check whether the phone's settings enabled location services for the app
        mySwitch = findViewById(R.id.locationSwitch);
        //Save switch state in shared preferences
        SharedPreferences mySharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        mySwitch.setChecked(mySharedPreferences.getBoolean("value", true));
        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                if (getSwitchStatus()) {
                    //When the switch is checked
                    editor.putBoolean("value", true);
                    editor.apply();
                    mySwitch.setChecked(true);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        alertMessageNoGps(locationManager);
                    }else{
                        // if the gps is enabled and location switch is turned on
                        updateLocation();
                    }
                }else {
                    //When the switch isn't checked
                    editor.putBoolean("value", false);
                    editor.apply();
                    mySwitch.setChecked(false);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(LocationSettings.this, "Enable location services on your phone's settings!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LocationSettings.this, "GPS is already enabled, please toggle switch!", Toast.LENGTH_SHORT).show();
                        myTextView2.setText("Latitude: ");
                        myTextView3.setText("Longitude: ");
                    }
                }
            }
        });
    }
    public void alertMessageNoGps(final LocationManager locationManager){
        //Alert dialog pops up if the location switch is on for the app but the location services is off for the phone settings
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setTitle("Location")
                .setMessage("GPS is disabled on your phone's settings, do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                            mySwitch.setChecked(false);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mySwitch.setChecked(false);
                        dialog.cancel();
                    }
                });
        myAlert.create().show();
    }
}