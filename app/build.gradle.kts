plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ai_plant_disease_detector"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ai_plant_disease_detector"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        // ✅ Kotlin DSL ka sahi syntax:
//        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE*"
            excludes += "/META-INF/NOTICE*"
            excludes += "/META-INF/io.netty.versions.properties"
        }
    }
}

dependencies {
    // TensorFlow Lite & CameraX
    implementation("com.google.guava:guava:31.0.1-android")
    implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // Gemini AI (SDK) & Guava
    implementation("com.google.genai:google-genai:1.32.0")
    implementation("com.google.guava:guava:31.1-android")

    // Core AndroidX
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Java 8+ features support
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}