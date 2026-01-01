//package com.example.ai_plant_disease_detector;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import java.io.File;
//import java.util.List;
//
//
//public class ProcessingActivity extends AppCompatActivity {
//
//    private static final String TAG = "ProcessingActivity";
//    private TextView txtProcessing;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_processing);
//
//        txtProcessing = findViewById(R.id.txtProcessing);
//        txtProcessing.setText("Starting processing...");
//
//        String imagePath = getIntent().getStringExtra("imagePath");
//
//        // Validation: Check if path exists
//        if (imagePath == null || !new File(imagePath).exists()) {
//            Toast.makeText(this, "Image file not found!", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        new Thread(() -> {
//            try {
//                // 1. Image Loading with Scaling (to prevent OutOfMemory)
//                updateStatus("Loading image...");
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2; // Image size half kar dega performance ke liye
//                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//
//                if (bitmap == null) {
//                    throw new Exception("Failed to decode Bitmap");
//                }
//
//                // 2. Model Loading
//                updateStatus("Loading AI model...");
//                // Context ke liye getApplicationContext() use karein
////                PlantDiseaseClassifier classifier = new PlantDiseaseClassifier(getApplicationContext());
//
//                // 3. Prediction
////                updateStatus("Predicting...");
////                String prediction = classifier.classify(bitmap);
//                YoloV8Detector detector =
//                        new YoloV8Detector(getApplicationContext());
//
//                List<YoloV8Detector.Detection> detections =
//                        detector.detect(bitmap);
//
//                StringBuilder resultText = new StringBuilder();
//
//                if (detections.isEmpty()) {
//                    resultText.append("No disease detected");
//                } else {
//                    for (YoloV8Detector.Detection d : detections) {
//                        resultText.append(d.label)
//                                .append(" - ")
//                                .append((int)(d.confidence * 100))
//                                .append("%\n");
//                    }
//                }
//
//
//
//                // 4. Success - Move to Result
//                runOnUiThread(() -> {
//                    Intent intent = new Intent(ProcessingActivity.this, ResultActivity.class);
//                    intent.putExtra("imagePath", imagePath);
//                    intent.putExtra("prediction", resultText.toString());
//
//                    startActivity(intent);
//                    finish();
//                });
//
//            } catch (Exception e) {
//                Log.e(TAG, "FATAL ERROR: " + e.getMessage(), e);
//                runOnUiThread(() -> {
//                    txtProcessing.setText("Error: " + e.getMessage());
//                    Toast.makeText(this, "Processing Failed!", Toast.LENGTH_LONG).show();
//                });
//            }
//        }).start();
//    }
//
//    private void updateStatus(String msg) {
//        runOnUiThread(() -> txtProcessing.setText(msg));
//    }
//}