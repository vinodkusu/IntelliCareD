package com.themahi.intellicare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.databinding.ActivitySplashBinding
import com.themahi.intellicare.ui.DemoActivity

/**
 * Splash Screen
 */
private const val SPLASH_MILLIS = 1000L

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(mainLooper).postDelayed({
            val intent = Intent(this@SplashActivity, DemoActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_MILLIS)
    }
}