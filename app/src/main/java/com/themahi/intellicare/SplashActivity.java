package com.themahi.intellicare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.databinding.ActivitySplashBinding;
import com.themahi.intellicare.ui.DemoActivity;
import com.themahi.intellicare.ui.registration.RegistrationActivity;

/**
 * Splash Screen
 */
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, DemoActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_MILLIS);
    }
}