package com.vcu.teamnapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
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

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // The xml resource that holds the preferences
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            // To get an instance of a preference, search for a preference using its key through PreferenceManager within onCreateView()
//            SwitchPreferenceCompat mySwitchPreference = (SwitchPreferenceCompat) getPreferenceManager().findPreference("location");
//            if (mySwitchPreference != null) {
//                mySwitchPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                    @Override
//                    public boolean onPreferenceClick(Preference preference) {
//                        return true;
//                    }
//                });
//            }
//            return inflater.inflate(R.layout.settings_activity, container, false);
//        }

    }
}