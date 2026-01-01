//package com.example.ai_plant_disease_detector;
//
//import android.content.Context;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Bitmap;
//
//import org.tensorflow.lite.Interpreter;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.ArrayList;
//import java.util.List;
//
//public class YoloV8Detector {
//
//    private static final int INPUT_SIZE = 640;
//    private static final int NUM_CLASSES = 20;
//    private static final int OUTPUT_BOXES = 8400; // YOLOv8 default
//    private static final int VALUES_PER_BOX = 5 + NUM_CLASSES;
//
//    private Interpreter interpreter;
//    private List<String> labels;
//
//    public static class Detection {
//        public float x1, y1, x2, y2;
//        public String label;
//        public float confidence;
//    }
//
//    public YoloV8Detector(Context context) throws IOException {
//        interpreter = new Interpreter(loadModel(context));
//        labels = loadLabels(context);
//    }
//
//    private MappedByteBuffer loadModel(Context context) throws IOException {
//        AssetFileDescriptor fileDescriptor =
//                context.getAssets().openFd("best_float16.tflite");
//        FileInputStream inputStream =
//                new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel channel = inputStream.getChannel();
//        return channel.map(
//                FileChannel.MapMode.READ_ONLY,
//                fileDescriptor.getStartOffset(),
//                fileDescriptor.getDeclaredLength()
//        );
//    }
//
//    private List<String> loadLabels(Context context) throws IOException {
//        List<String> list = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(context.getAssets().open("labels.txt"))
//        );
//        String line;
//        while ((line = reader.readLine()) != null) {
//            list.add(line);
//        }
//        reader.close();
//        return list;
//    }
//
//    public List<Detection> detect(Bitmap bitmap) {
//
//        Bitmap resized = Bitmap.createScaledBitmap(
//                bitmap, INPUT_SIZE, INPUT_SIZE, true);
//
//        ByteBuffer inputBuffer =
//                ByteBuffer.allocateDirect(1 * INPUT_SIZE * INPUT_SIZE * 3 * 4);
//        inputBuffer.order(ByteOrder.nativeOrder());
//
//        for (int y = 0; y < INPUT_SIZE; y++) {
//            for (int x = 0; x < INPUT_SIZE; x++) {
//                int px = resized.getPixel(x, y);
//                inputBuffer.putFloat(((px >> 16) & 0xFF) / 255f);
//                inputBuffer.putFloat(((px >> 8) & 0xFF) / 255f);
//                inputBuffer.putFloat((px & 0xFF) / 255f);
//            }
//        }
//
//        float[][][] output =
//                new float[1][OUTPUT_BOXES][VALUES_PER_BOX];
//
//        interpreter.run(inputBuffer, output);
//
//        List<Detection> detections = new ArrayList<>();
//
//        for (int i = 0; i < OUTPUT_BOXES; i++) {
//
//            float confidence = output[0][i][4];
//            if (confidence < 0.4f) continue;
//
//            int classId = -1;
//            float maxClassScore = 0;
//
//            for (int c = 0; c < NUM_CLASSES; c++) {
//                float score = output[0][i][5 + c];
//                if (score > maxClassScore) {
//                    maxClassScore = score;
//                    classId = c;
//                }
//            }
//
//            if (classId < 0 || classId >= labels.size()) continue;
//
//            float cx = output[0][i][0];
//            float cy = output[0][i][1];
//            float w  = output[0][i][2];
//            float h  = output[0][i][3];
//
//            Detection det = new Detection();
//            det.x1 = cx - w / 2;
//            det.y1 = cy - h / 2;
//            det.x2 = cx + w / 2;
//            det.y2 = cy + h / 2;
//            det.label = labels.get(classId);
//            det.confidence = confidence;
//
//            detections.add(det);
//        }
//
//        return detections;
//    }
//}
