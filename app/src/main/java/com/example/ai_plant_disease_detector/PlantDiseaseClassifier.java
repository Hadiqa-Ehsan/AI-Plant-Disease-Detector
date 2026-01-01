//package com.example.ai_plant_disease_detector;
//
//import android.content.Context;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Bitmap;
//import org.tensorflow.lite.Interpreter;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//
//public class PlantDiseaseClassifier {
//
//    private Interpreter interpreter;
//    private static final int INPUT_SIZE = 640;
//    private static final int NUM_BOXES = 8400;
//    // Error ke mutabiq model 4 values de raha hai (x, y, w, h)
//    private static final int OUTPUT_FEATURES = 4;
//
//    public PlantDiseaseClassifier(Context context) throws IOException {
//        // Model load karna
//        interpreter = new Interpreter(loadModelFile(context));
//    }
//
//    private MappedByteBuffer loadModelFile(Context context) throws IOException {
//        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("best_float32.tflite");
//        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel fileChannel = inputStream.getChannel();
//        long startOffset = fileDescriptor.getStartOffset();
//        long declaredLength = fileDescriptor.getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
//    }
//
//    public String classify(Bitmap bitmap) {
//        // 1. Image ko model ke size (640x640) par resize karein
//        Bitmap resized = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
//
//        // 2. Input Tensor taiyar karein [1, 640, 640, 3]
//        float[][][][] input = new float[1][INPUT_SIZE][INPUT_SIZE][3];
//        for (int y = 0; y < INPUT_SIZE; y++) {
//            for (int x = 0; x < INPUT_SIZE; x++) {
//                int px = resized.getPixel(x, y);
//                // Normalization (0-1 range)
//                input[0][y][x][0] = ((px >> 16) & 0xFF) / 255f;
//                input[0][y][x][1] = ((px >> 8) & 0xFF) / 255f;
//                input[0][y][x][2] = (px & 0xFF) / 255f;
//            }
//        }
//
//        // 3. Output Buffer [1, 4, 8400] - Ye wahi shape hai jo aapka error maang raha tha
//        float[][][] output = new float[1][OUTPUT_FEATURES][NUM_BOXES];
//
//        // 4. Model run karein
//        try {
//            interpreter.run(input, output);
//        } catch (Exception e) {
//            return "Interpreter Error: " + e.getMessage();
//        }
//
//        // 5. Data extract karein (Pehle box ki misaal)
//        // Note: Model [1,4,8400] ka matlab hai ye 8400 possible boxes ke coordinates de raha hai
//        float x = output[0][0][0];
//        float y = output[0][1][0];
//        float w = output[0][2][0];
//        float h = output[0][3][0];
//
//        // Result wapis bhejein
//        return "Model Match! Detection successful.\nTop Box: x=" + String.format("%.2f", x) + ", y=" + String.format("%.2f", y);
//    }
//}