package com.vcu.teamnapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity2 extends AppCompatActivity {
    private static TextView myTextView;
    private static Switch mySwitch;
    private static boolean location_ON;
    private static boolean location_OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        seekBar();
        checkLocationServices();
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
    public void checkLocationServices(){
        //check whether the phone's settings enabled location services for the app (code needs to be revised)
        final LocationManager MYMANGER =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                location_OFF = !isChecked;
                location_ON = isChecked;
                if (location_OFF){
                    if (!MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        Toast.makeText(SettingsActivity2.this, "Enable location services on your phone's settings!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SettingsActivity2.this, "GPS is already enabled, please toggle switch!", Toast.LENGTH_SHORT).show();
                    }
                }else if (location_ON){
                    if (!MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        alertMessageNoGps();
                    }else{
                        // if the gps is enabled and location switch is turned on
                        getCurrentLocation();
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