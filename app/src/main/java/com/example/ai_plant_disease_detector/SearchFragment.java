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
        // Layout file ka naam check karlein (fragment_search ya search_fragment)
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        etDiseaseName = view.findViewById(R.id.etDiseaseName); // Aapki XML mein ID yahi hai
        btnAskGemini = view.findViewById(R.id.btnAskGemini);  // Aapki XML mein ID yahi hai

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
                    .apiKey("AIzaSyB12E1Ki65zZtBTvr6qcWVydgnB5cxH3l0")
                    .build();
        }

        Toast.makeText(requireContext(), "Consulting AI Plant Pathologist...", Toast.LENGTH_SHORT).show();

        // Professional Plant Expert Prompt
        String systemInstruction =
                "You are a Senior Plant Pathologist. Generate a highly structured 'Plant Health Diagnostic Report'.\n" +
                        "Format the response using these exact headers for clarity:\n\n" +
                        "📌 DISEASE IDENTIFIED: [Common Name & Scientific Name]\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                        "🔍 SYMPTOMS & DIAGNOSIS:\n" +
                        "- Provide 3-4 bullet points describing visible signs.\n\n" +
                        "🌿 ORGANIC/BIOLOGICAL CONTROL:\n" +
                        "- Suggest eco-friendly and home-based remedies.\n\n" +
                        "🧪 CHEMICAL TREATMENT:\n" +
                        "- Mention specific active ingredients (fungicides/pesticides) with safe usage tips.\n\n" +
                        "🛡️ PREVENTION & LONG-TERM CARE:\n" +
                        "- Provide steps to ensure the disease does not return.\n\n" +
                        "⚠️ IMPORTANT NOTE: If the user query is not about plants, reply: 'I am specialized in plant health only. Please provide a plant-related query.'";
        String finalPrompt = systemInstruction + "\n\nUser Question: " + prompt;

        new Thread(() -> {
            try {
                GenerateContentResponse response =
                        genAiClient.models.generateContent(
                                "gemini-2.5-flash",
                                finalPrompt,
                                null
                        );

                String aiText = response.text();

                if (!isAdded()) return;

                requireActivity().runOnUiThread(() -> {
                    if (aiText != null && !aiText.isEmpty()) {
                        // Response ko display activity mein bhejna
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