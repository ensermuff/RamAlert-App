package com.vcu.teamnapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class chip_group extends AppCompatActivity {
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chip_group);
        backButton = findViewById(R.id.backButton);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(chip_group.this,MainActivity.class);
        startActivity(intent);
    }
}

