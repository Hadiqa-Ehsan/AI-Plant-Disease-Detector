package com.example.ai_plant_disease_detector;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class AIDisplayActivity extends AppCompatActivity {

    private TextView tvAIResponse;
    private MaterialButton btnNewAnalysis; // ✅ ID updated to match your new XML

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_display);

        tvAIResponse = findViewById(R.id.tvAIResponse);

        // ✅ XML mein ID "btnNewAnalysis" hai, isliye yahan wahi use karni hai
        btnNewAnalysis = findViewById(R.id.btnNewAnalysis);

        // 🔹 Intent se data lena
        String diseaseName = getIntent().getStringExtra("disease_name");
        String aiReport = getIntent().getStringExtra("ai_result");

        if (diseaseName != null) {
            tvAIResponse.setText("Generating expert report for: " + diseaseName + "...");
            // Yahan aap apna AI function call kar sakti hain
        } else if (aiReport != null && !aiReport.isEmpty()) {
            tvAIResponse.setText(aiReport);
        } else {
            tvAIResponse.setText("Sorry, no diagnostic report was found.");
        }

        // ✅ Button click logic (Ab error nahi aayega)
        if (btnNewAnalysis != null) {
            btnNewAnalysis.setOnClickListener(v -> finish());
        }
    }
}