package com.vcu.RamAlerts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class EmergencyVCUNumbersActivity extends AppCompatActivity {

    Button onCampusButton;
    Button offCampusButton;
    Button vcuPDButton;
    Button rvaPDButton;
    Button ramsafeButton;
    Button counselingServicesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_vcu_numbers);
        onCampusButton = findViewById(R.id.onCampusButton);
        offCampusButton = findViewById(R.id.offCampusButton);
        vcuPDButton = findViewById(R.id.vcuPDButton);
        rvaPDButton = findViewById(R.id.rvaPDButton);
        ramsafeButton = findViewById(R.id.ramsafeButton);
        counselingServicesButton = findViewById(R.id.counselingServicesButton);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(EmergencyVCUNumbersActivity.this, MainActivity.class);
    }
    public void callOnCampusEmergency(View view) {
        String number = "8048281234";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void callOffCampusEmergency(View view) {
        String number = "911";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void callVCUPD(View view) {
        String number = "8048281196";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void callRVAPD(View view) {
        String number = "8046465100";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void callRamsafe(View view) {
        String number = "8048287233";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void callCounselingServices(View view) {
        String number = "8048286200";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(number));
        startActivity(intent);
    }

    public void onClickHandler(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        if(id == R.id.action_settings){
            intent = new Intent(this, LocationSettings.class);
            startActivity(intent);
        }
        else if(id == R.id.closeMenu){
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
}