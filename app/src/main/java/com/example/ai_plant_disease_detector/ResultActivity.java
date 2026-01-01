package com.example.ai_plant_disease_detector;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar; // ✅ Added
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.tensorflow.lite.Interpreter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private static final int INPUT_SIZE = 200;
    private static final int NUM_CLASSES = 39;

    private Interpreter tflite;
    private List<String> labels;

    ImageView imageView;
    TextView resultText, tvConfidence, tvDiseaseDetails;
    ProgressBar confidenceBar; // ✅ Added for new UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // ✅ Linking with new XML IDs
        imageView = findViewById(R.id.resultImage);
        resultText = findViewById(R.id.resultText);
        tvConfidence = findViewById(R.id.tvConfidence);
        confidenceBar = findViewById(R.id.confidenceBar);
        tvDiseaseDetails = findViewById(R.id.tvDiseaseDetails);

        // ❌ REMOVED: btnGetTreatment, btnNewScan, etc. (Ab ye error nahi denge)

        try {
            tflite = new Interpreter(loadModelFile());
            labels = loadLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imagePath = getIntent().getStringExtra("image_path");
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
            classifyImage(bitmap);
        }
    }

    private void classifyImage(Bitmap bitmap) {
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resized);
        float[][] output = new float[1][NUM_CLASSES];

        tflite.run(inputBuffer, output);

        int maxIndex = 0;
        float maxProb = 0;
        for (int i = 0; i < NUM_CLASSES; i++) {
            if (output[0][i] > maxProb) {
                maxProb = output[0][i];
                maxIndex = i;
            }
        }

        String label = labels.get(maxIndex);
        int confidence = (int) (maxProb * 100);

        // ✅ Updating new UI elements
        resultText.setText(label);
        tvConfidence.setText(confidence + "%");
        confidenceBar.setProgress(confidence);

        // Dynamic description (Example)
        tvDiseaseDetails.setText("The plant appears to be affected by " + label + ". Detailed description and treatment steps will be shown here.");
    }

    // ... (convertBitmapToByteBuffer, loadModelFile, loadLabels functions stay the same)

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3);
        buffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(pixels, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE);
        int pixelIndex = 0;
        for (int i = 0; i < INPUT_SIZE; i++) {
            for (int j = 0; j < INPUT_SIZE; j++) {
                int pixel = pixels[pixelIndex++];
                buffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f);
                buffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);
                buffer.putFloat((pixel & 0xFF) / 255.0f);
            }
        }
        return buffer;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }

    private List<String> loadLabels() throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
        String line;
        while ((line = reader.readLine()) != null) { labelList.add(line); }
        reader.close();
        return labelList;
    }
}