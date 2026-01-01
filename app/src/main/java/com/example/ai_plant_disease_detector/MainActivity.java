package com.example.ai_plant_disease_detector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView; // ✅ CardView import lazmi hai

public class MainActivity extends AppCompatActivity {

    // ✅ Button ki jagah CardView likha hai taake error khatam ho jaye
    CardView btnScan, btnSearchAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1️⃣ CardViews ko link karein (IDs wahi hain jo aapne XML mein rakhi thin)
        btnScan = findViewById(R.id.cardScan);
        btnSearchAI = findViewById(R.id.cardSearch);

        // 2️⃣ Scan Card logic
        btnScan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
            try {
                // TFLite model logic yahan handle ho sakti hai
            } catch (Exception e) {
                Log.e("TFLITE_ERROR", e.getMessage());
            }
        });

        // 3️⃣ AI Search Card logic
        btnSearchAI.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Poori screen replace karna
            transaction.replace(android.R.id.content, searchFragment);

            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
}