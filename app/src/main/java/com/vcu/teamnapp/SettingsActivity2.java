package com.vcu.teamnapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity2 extends AppCompatActivity {
    private static TextView myTextView;

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
        //not complete
        final LocationManager MYMANGER =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Switch mySwitch = (Switch) findViewById(R.id.switch1);
        boolean location_OFF = !mySwitch.isChecked();
        boolean location_ON = mySwitch.isChecked();
        if (location_ON && !MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
           alertMessageNoGps();
        }else if (location_OFF && !MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(SettingsActivity2.this, "Toggle switch in order to enable location services", Toast.LENGTH_SHORT).show();
        }else if (location_OFF && MYMANGER.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(SettingsActivity2.this, "GPS is already enabled, please toggle switch!", Toast.LENGTH_SHORT).show();
        }else {
            // if the gps is enabled and location switch is turned on
            alertMessageNoGps();
        }
    }
    public void alertMessageNoGps(){
        //not complete
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setTitle("Location")
                .setMessage("GPS is disabled, do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();;
                    }
                });
        myAlert.show();
    }
}