package com.vcu.teamnapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_settings, new SettingsFragment())
                .commit();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // This closes the current activity and returns to the main activity
        return true;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements PreferenceManager.OnPreferenceTreeClickListener  {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // The xml resource that holds the preferences
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
        @Override
        public boolean onPreferenceTreeClick(androidx.preference.Preference preference) {
            String key = preference.getKey();

            if (key != null && key.equals("location")){
                Intent myIntent = new Intent(SettingsFragment.this.getActivity(), MainActivity.class);
                startActivity(myIntent);
                return true;
            } else if (key != null && key.equals("notifications")){
                Intent myIntent2 = new Intent(SettingsFragment.this.getActivity(), EmergencyVCUNumbersActivity.class);
                startActivity(myIntent2);
                return true;
            }

            return false;
        }
    }
}