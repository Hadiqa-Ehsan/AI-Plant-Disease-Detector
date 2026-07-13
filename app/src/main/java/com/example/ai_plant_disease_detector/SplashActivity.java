package com.example.ai_plant_disease_detector;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.cardview.widget.CardView; // Agar aap CardView use kar rahi hain

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
// onCreate method ke andar ye code dalein
        ImageView leafIcon = findViewById(R.id.leaf_icon); // Ya phir CardView ki ID
        Animation floating = AnimationUtils.loadAnimation(this, R.anim.floating_anim);
        leafIcon.startAnimation(floating);
        // Background Leaves Float (Inhein thoda slow ya different feel dene ke liye)
        findViewById(R.id.bg_leaf_1).startAnimation(floating);
        findViewById(R.id.bg_leaf_2).startAnimation(floating);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2500); // 2.5 seconds
    }
}
