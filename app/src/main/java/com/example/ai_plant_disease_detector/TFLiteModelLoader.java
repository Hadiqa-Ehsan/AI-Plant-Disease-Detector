//package com.example.ai_plant_disease_detector;
//
//import android.content.Context;
//
//import org.tensorflow.lite.Interpreter;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//
//public class TFLiteModelLoader {
//
//    private Interpreter interpreter;
//
//    public TFLiteModelLoader(Context context) throws IOException {
//        MappedByteBuffer modelBuffer = loadModelFile(context, "best_float16.tflite");
//        Interpreter.Options options = new Interpreter.Options();
//        options.setNumThreads(4);
//        interpreter = new Interpreter(modelBuffer, options);
//    }
//
//    private MappedByteBuffer loadModelFile(Context context, String modelName) throws IOException {
//        FileInputStream inputStream =
//                new FileInputStream(context.getAssets().openFd(modelName).getFileDescriptor());
//        FileChannel fileChannel = inputStream.getChannel();
//        long startOffset = context.getAssets().openFd(modelName).getStartOffset();
//        long declaredLength = context.getAssets().openFd(modelName).getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
//    }
//
//    public Interpreter getInterpreter() {
//        return interpreter;
//    }
//}
