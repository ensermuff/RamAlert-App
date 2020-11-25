package com.vcu.RamAlerts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;


//Main Activity class
public class MainActivity extends AppCompatActivity{
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment displayAlert = new DisplayAlertFragment();
        setContentView(R.layout.nav_activity_main);
        initializeDrawer();
        InboxReader retrieveMessage = new InboxReader();
        if (!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }
        retrieveMessage.sendVcuAlert(this);
        Toast.makeText(this, retrieveMessage.messageBody, Toast.LENGTH_SHORT).show();

        ImageButton fab = findViewById(R.id.menuId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }
        });

    }

    private void initializeDrawer() {
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(t);
        t.syncState();
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
                intent = new Intent(this, LocationSettings.class);
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
    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
    }
}
