package com.example.ai_plant_disease_detector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class SearchFragment extends Fragment {

    private EditText etDiseaseName;
    private Button btnAskGemini;
    private Client genAiClient;

    public SearchFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        etDiseaseName = view.findViewById(R.id.etDiseaseName);
        btnAskGemini = view.findViewById(R.id.btnAskGemini);

        btnAskGemini.setOnClickListener(v -> {
            String input = etDiseaseName.getText().toString().trim();
            askGemini(input);
        });
        return view;
    }

    private void askGemini(String prompt) {
        if (prompt.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a disease name...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (genAiClient == null) {
            genAiClient = Client.builder()
                    .apiKey("AIzaSyDwc0zJnSdXeU8VzC3C-SkYa9XxEyJHsEY")
                    .build();
        }

        Toast.makeText(requireContext(), "Consulting AI Plant Pathologist...", Toast.LENGTH_SHORT).show();

        // New Logic: AI ko 3 sections mein data dene ka instruction diya hai
        String finalPrompt = "You are a Senior Plant Pathologist. Generate a structured 'Plant Health Diagnostic Report'.\n" +
                "IMPORTANT: Split the response into exactly these 3 tags: [SECTION1], [SECTION2], [SECTION3].\n\n" +
                "[SECTION1]\n📌 DISEASE IDENTIFIED: (Name)\n🔍 SYMPTOMS: (3 bullet points)\n\n" +
                "[SECTION2]\n(Provide detailed Organic and Chemical Treatment here)\n\n" +
                "[SECTION3]\n(Provide long-term Prevention tips here)\n\n" +
                "User Query: " + prompt + "\n\n" +
                "⚠️ If query is not plant-related, reply: 'I am specialized in plant health only.'";

        new Thread(() -> {
            try {
                // Corrected model to gemini-1.5-flash
                GenerateContentResponse response = genAiClient.models.generateContent(
                        "gemini-2.5-flash",
                        finalPrompt,
                        null
                );

                String aiText = response.text();
                if (!isAdded()) return;

                requireActivity().runOnUiThread(() -> {
                    if (aiText != null && !aiText.isEmpty()) {
                        Intent intent = new Intent(requireActivity(), AIDisplayActivity.class);
                        intent.putExtra("ai_result", aiText);
                        startActivity(intent);
                        etDiseaseName.setText("");
                    } else {
                        Toast.makeText(requireContext(), "AI could not generate a report.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("GEMINI_ERROR", e.getMessage(), e);
                if (!isAdded()) return;
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Expert Consultation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }
}