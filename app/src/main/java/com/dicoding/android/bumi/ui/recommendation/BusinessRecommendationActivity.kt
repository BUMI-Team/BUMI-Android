package com.dicoding.android.bumi.ui.recommendation

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
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityBusinessRecommendationBinding
import com.dicoding.android.bumi.databinding.ActivityLoginBinding

class BusinessRecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessRecommendationBinding.inflate(layoutInflater)
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
        binding.btnRekomKonfirm.setOnClickListener {
            val jenisUsaha = binding.etPengalamanKerja.text.toString()
            val hobi = binding.etHobi.text.toString()
            when {
                jenisUsaha.isEmpty() -> {
                    binding.pengalamanKerjaTextInputLayout.error = "Masukkan pengalaman kerja anda"
                }
                hobi.isEmpty() -> {
                    binding.hobiTextInputLayout.error = "Masukkan hobi anda"
                }
                else -> {
                    popUpDialog()
                }
            }
        }
    }

    private fun popUpDialog() {
        val popUpDialog = Dialog(this)
        popUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog.setContentView(R.layout.popup_hasil_rekomendasi)

        val btnKonfirm = popUpDialog.findViewById<Button>(R.id.btn_konfirm_hasil_rekom)
        btnKonfirm.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        popUpDialog.show()
    }
}