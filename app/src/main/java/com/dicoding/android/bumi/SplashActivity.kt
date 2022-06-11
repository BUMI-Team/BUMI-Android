package com.dicoding.android.bumi

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.android.bumi.ui.onboarding.OnboardingActivity
import com.dicoding.android.bumi.ui.signin.SigninActivity
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        Handler().postDelayed({
            val sharedPreference = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedLoginState = sharedPreference.getString("STRING_KEY", "onboarding")
            when (savedLoginState) {
                "onboarding" -> {
                    val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    startActivity(intent)
                }
                "login" -> {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
//            val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
//            val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)

//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            val intent = Intent(this@SplashActivity, SigninActivity::class.java)
//            startActivity(intent)

        }, 3000)

        setupView()
    }

    // Fullscreen
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}