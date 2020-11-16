package com.vcu.RamAlerts;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class EmergencyVCUNumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_vcu_numbers);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(EmergencyVCUNumbersActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickHandler(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        if(id == R.id.action_settings){
            intent = new Intent(this, SettingsActivity2.class);
            startActivity(intent);
        }
        else if(id == R.id.closeMenu){
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

}