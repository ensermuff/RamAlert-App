package com.vcu.teamnapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyVCUNumbersActivity extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_vcu_numbers);
        backButton = findViewById(R.id.backButton);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(EmergencyVCUNumbersActivity.this,MainActivity.class);
        startActivity(intent);
    }

    // don't forget a button from main that leads to this activity
}