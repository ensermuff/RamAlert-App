package com.vcu.RamAlerts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


//Main Activity class
public class MainActivity extends AppCompatActivity{
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(t);
        t.syncState();
        ImageButton fab = findViewById(R.id.menuId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
    public void onClickHandler(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.location_settings:
                intent = new Intent(this, SettingsActivity2.class);
                startActivity(intent);
                break;

            case R.id.closeMenu:
            case R.id.alerts_list:
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;

            case R.id.vcu_phone_numbers:
                intent = new Intent(this, EmergencyVCUNumbersActivity.class);
                startActivity(intent);
                break;
        }
        }

}
