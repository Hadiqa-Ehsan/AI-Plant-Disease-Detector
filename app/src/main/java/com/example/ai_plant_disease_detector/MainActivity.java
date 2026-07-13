package com.example.ai_plant_disease_detector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView cardScanLeaf, cardAICure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // IDs ko link karein (Zaroori: XML wali IDs se match karein)
        cardScanLeaf = findViewById(R.id.cardScan);
        cardAICure = findViewById(R.id.cardAI); // Agar XML mein ID 'cardSearch' hai to wo likhein

        // 1. SCAN LEAF -> CameraActivity
        if (cardScanLeaf != null) {
            cardScanLeaf.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Camera Activity missing!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 2. AI CURE -> AiDisplayActivity
        if (cardAICure != null) {
            cardAICure.setOnClickListener(v -> {
                try {
                    // Fragment ko kholne ka sahi tareeqa yeh hai:
                    SearchFragment searchFragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, searchFragment) // android.R.id.content poori screen replace kar dega
                            .addToBackStack(null)
                            .commit();
                } catch (Exception e) {
                    Toast.makeText(this, "Fragment Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}