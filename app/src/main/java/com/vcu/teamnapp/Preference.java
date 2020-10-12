package com.vcu.teamnapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class Preference extends PreferenceFragmentCompat implements PreferenceManager.OnPreferenceTreeClickListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public boolean onPreferenceTreeClick(androidx.preference.Preference preference) {
        String key = preference.getKey();

        if (key != null && key.equals("location")){
            Intent myIntent = new Intent(Preference.this.getActivity(), MainActivity.class);
            startActivity(myIntent);
            return true;
        } else if (key != null && key.equals("notifications")){
            Intent myIntent2 = new Intent(Preference.this.getActivity(), EmergencyVCUNumbersActivity.class);
            startActivity(myIntent2);
            return true;
        }

        return false;
    }
}
