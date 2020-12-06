package com.vcu.RamAlerts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;

public class ChipGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_group);
    }
    private void addChipView(String chipText) {
        ViewGroup chipGroup;
        Chip chip = (Chip) getLayoutInflater().inflate(R.layout.activity_chip_group, com.google.android.material.chip.ChipGroup, false);
        chip.setText(chipText);
        //...

        // This is ChipGroup view
        com.google.android.material.chip.ChipGroup.addChipView(chip);
    }
//
//    public void goBackToMain(View view) {
//        Intent intent = new Intent(ChipGroup.this,MainActivity.class);
//        startActivity(intent);
//    }
}