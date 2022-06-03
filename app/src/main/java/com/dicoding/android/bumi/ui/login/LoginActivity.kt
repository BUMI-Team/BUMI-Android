package com.dicoding.android.bumi.ui.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityLoginBinding
import com.dicoding.android.bumi.ui.recommendation.BusinessDetailActivity
import com.dicoding.android.bumi.ui.recommendation.BusinessRecommendationActivity
import com.dicoding.android.bumi.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

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

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etlEmail.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etlPassword.error = "Masukkan password"
                }
//                email != user.email -> {
//                    binding.etlEmail.error = "Email tidak sesuai"
//                }
//                password != user.password -> {
//                    binding.etlPassword.error = "Password tidak sesuai"
//                }
                else -> {
                    // Login
                    // loginViewModel.login()
                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, PopUpAfterLoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
                    popUpDialog()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun popUpDialog() {
        val popUpDialog = Dialog(this)
        popUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog.setContentView(R.layout.popup_after_login)

        val btnBelum = popUpDialog.findViewById<Button>(R.id.button_belum)
        val btnSudah = popUpDialog.findViewById<Button>(R.id.button_sudah)
        btnBelum.setOnClickListener{
            val intent = Intent(this, BusinessRecommendationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        btnSudah.setOnClickListener{
            val intent = Intent(this, BusinessDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        popUpDialog.show()
    }
}