package com.dicoding.android.bumi.ui.recommendation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.databinding.ActivityBusinessDetailBinding

class BusinessDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessDetailBinding.inflate(layoutInflater)
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
        binding.btnDetailKonfirm.setOnClickListener {
            val jenisUsaha = binding.etJenisUsaha.text.toString()
            val hobi = binding.etHobi.text.toString()
            when {
                jenisUsaha.isEmpty() -> {
                    binding.jenisUsahaTextInputLayout.error = "Masukkan jenis usaha anda"
                }
                hobi.isEmpty() -> {
                    binding.hobiTextInputLayout.error = "Masukkan hobi anda"
                }
                else -> {
                    Toast.makeText(this@BusinessDetailActivity, "Terima kasih sudah melengkapi data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}