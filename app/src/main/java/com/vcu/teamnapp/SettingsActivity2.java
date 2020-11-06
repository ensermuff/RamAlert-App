package com.vcu.teamnapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class SettingsActivity2 extends AppCompatActivity {
    private static TextView myTextView;
    private static Switch mySwitch;
    private static boolean location_ON;
    private static boolean location_OFF;
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        seekBar();
        checkLocationServices(locationManager);
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
                Toast.makeText(SettingsActivity2.this, "SeekBar is in progress", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity2.this, "Begin tracking ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity2.this, "End tracking ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checkLocationServices(final LocationManager MYMANGER){
        //check whether the phone's settings enabled location services for the app
        mySwitch = (Switch) findViewById(R.id.switch1);
        //Save switch state in shared preferences
        SharedPreferences mySharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        mySwitch.setChecked(mySharedPreferences.getBoolean("value", true));
        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_OFF = !mySwitch.isChecked();
                location_ON = mySwitch.isChecked();
                if (location_ON) {
                    //When the switch is checked
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    mySwitch.setChecked(true);
                    if (!MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        alertMessageNoGps();
                    }else{
                        // if the gps is enabled and location switch is turned on
                        getCurrentLocation();
                    }
                }else if (location_OFF) {
                    //When the switch isn't checked
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    mySwitch.setChecked(false);
                    if (!MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(SettingsActivity2.this, "Enable location services on your phone's settings!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SettingsActivity2.this, "GPS is already enabled, please toggle switch!", Toast.LENGTH_SHORT).show();
                    }
                    if (location_OFF) {
                        if (!MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            Toast.makeText(SettingsActivity2.this, "Enable location services on your phone's settings!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SettingsActivity2.this, "GPS is already enabled, please toggle switch!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public void alertMessageNoGps(){
        //Alert dialog pops up if the location switch is on for the app but the location services is off for the phone settings
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setTitle("Location")
                .setMessage("GPS is disabled on your phone's settings, do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mySwitch.setChecked(location_OFF);
                        dialog.cancel();
                    }
                });
        myAlert.create().show();
        getCurrentLocation();
    }
    public void getCurrentLocation(){
        // getting location

    }
}