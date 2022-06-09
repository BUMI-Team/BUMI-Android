package com.dicoding.android.bumi.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.android.bumi.databinding.ActivityWelcomeBinding
import com.dicoding.android.bumi.ui.signin.SigninActivity
import com.dicoding.android.bumi.ui.signup.SignupActivity
import com.google.android.gms.common.SignInButton


class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupView()
        setupAction()
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

    // Button
    private fun setupAction() {
        binding.btnSignupEmail.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.btnSignupGoogle.setOnClickListener {
//            signUp()
            Toast.makeText(this@WelcomeActivity, "Coming Soon", Toast.LENGTH_SHORT).show()

        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "WelcomeActivity"
    }
}