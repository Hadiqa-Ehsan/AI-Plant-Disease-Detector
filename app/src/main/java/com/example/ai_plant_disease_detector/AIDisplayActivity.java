package com.example.ai_plant_disease_detector;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AIDisplayActivity extends AppCompatActivity {

    private TextView tvAIResponse, tvTreatment, tvPrevention;
    private CardView btnNewAnalysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_display);

        // IDs link karein
        tvAIResponse = findViewById(R.id.tvAIResponse);
        tvTreatment = findViewById(R.id.tvTreatment);
        tvPrevention = findViewById(R.id.tvPrevention);
        btnNewAnalysis = findViewById(R.id.btnNewAnalysis);

        // Intent se data lena
        String aiReport = getIntent().getStringExtra("ai_result");

        if (aiReport != null && !aiReport.isEmpty()) {
            if (aiReport.contains("I am specialized in plant health only")) {
                tvAIResponse.setText(aiReport);
                tvTreatment.setText("Not available for this query.");
                tvPrevention.setText("Not available for this query.");
            } else {
                parseAndSetAIReport(aiReport);
            }
        } else {
            tvAIResponse.setText("Sorry, no diagnostic report was found.");
        }

        // Back button logic
        if (btnNewAnalysis != null) {
            btnNewAnalysis.setOnClickListener(v -> finish());
        }
    }

    // New Logic: AI response ko 3 boxes mein divide karne ka function
    private void parseAndSetAIReport(String report) {
        try {
            // Text ko [SECTION] tags ke mutabiq split karna
            String[] parts = report.split("\\[SECTION\\d\\]");

            // parts[0] aksar khali hota hai, data 1, 2, 3 mein hota hai
            if (parts.length >= 4) {
                tvAIResponse.setText(parts[1].trim());  // Box 1: Analysis
                tvTreatment.setText(parts[2].trim());   // Box 2: Treatment
                tvPrevention.setText(parts[3].trim());  // Box 3: Prevention
            } else {
                // Fallback: Agar AI ne tags nahi lagaye to poora text pehle box mein
                tvAIResponse.setText(report);
            }
        } catch (Exception e) {
            tvAIResponse.setText(report);
        }
    }
}