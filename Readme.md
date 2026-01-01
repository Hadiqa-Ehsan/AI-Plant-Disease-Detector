
---

# 🌿 PlantDoc AI: Plant Disease Detector

**PlantDoc AI** is an intelligent Android application designed to detect plant diseases using Machine Learning and Artificial Intelligence. Built for farmers, gardeners, and plant enthusiasts, it provides instant diagnosis and expert treatment recommendations.

---

## ✨ Features

* 📸 **Instant Disease Scanning**: Real-time scanning of plant leaves using the mobile camera for immediate detection.
* 🖼️ **Gallery Upload**: Option to upload existing leaf images from the gallery for diagnosis.
* 🧠 **Deep Learning Model**: Utilizes a **TensorFlow Lite (TFLite)** model capable of identifying **38+ plant disease classes** with high accuracy.
* 🤖 **AI Expert Consultation**: Integrated with **Gemini AI** to provide detailed treatment plans and prevention tips based on the detected disease.
* 📊 **Confidence Score**: Displays a confidence percentage and a professional analysis report for every scan.
* 🎨 **Modern UI/UX**: A clean, scannable, and easy-to-use interface built with **Material Design 3**.

---

## 🛠️ Tech Stack

* **Language**: Java (Android SDK)
* **Machine Learning**: TensorFlow Lite (TFLite)
* **Camera Framework**: Jetpack CameraX
* **UI Components**: Material Design, CoordinatorLayout, CardViews
* **AI Integration**: Google Gemini AI API (for expert medical/treatment reports)
* **Architecture**: Activity & Fragment-based lifecycle management

---

## 🚀 Installation & Setup

Follow these steps to get the project running locally:

1. **Clone the Repository**:
```bash
git clone https://github.com/yourusername/plant-disease-detector.git

```


2. **Open in Android Studio**:
   Open Android Studio and select "Open Project," then navigate to the cloned folder.
3. **Add Model Assets**:
   Ensure that `model.tflite` and `labels.txt` are placed in the `app/src/main/assets/` directory.
4. **Sync Gradle**:
   Wait for Android Studio to sync Gradle files and download necessary CameraX and TensorFlow dependencies.
5. **Run the App**:
   Connect your Android device or start an emulator and click the **Run** button.

---

## 📂 Project Structure

```text
app/
├── src/main/
│   ├── java/com/example/ai_plant_disease_detector/
│   │   ├── MainActivity.java      # Dashboard & Feature Selection
│   │   ├── CameraActivity.java    # CameraX & Image Capture Logic
│   │   ├── ResultActivity.java    # TFLite Inference & Classification
│   │   └── AIDisplayActivity.java # Gemini AI Treatment Reports
│   ├── res/layout/                # Professional XML UI Layouts
│   └── assets/                    # TFLite Model & Label Files

```

---

## 📜 Permissions Required

To function correctly, the app requires the following permissions:

* `CAMERA`: To scan leaves in real-time.
* `READ_EXTERNAL_STORAGE`: To pick leaf images from the device gallery.
* `INTERNET`: To communicate with Gemini AI for treatment advice.

---

## 🤝 Contributing

Contributions are welcome! If you want to improve this project:

1. **Fork** the project.
2. Create a **Feature Branch**.
3. **Commit** your changes.
4. Open a **Pull Request**.

---
