package com.vcu.RamAlerts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmergencyVCUNumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_vcu_numbers);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(EmergencyVCUNumbersActivity.this,MainActivity.class);
        startActivity(intent);
    }
}