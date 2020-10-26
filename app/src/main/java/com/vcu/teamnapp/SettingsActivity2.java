package com.vcu.teamnapp;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity2 extends AppCompatActivity {
    private static SeekBar mySeekBar;
    private static TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        seekBar();
    }

    public void seekBar() {
        mySeekBar = (SeekBar) findViewById(R.id.seekBar);
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
}