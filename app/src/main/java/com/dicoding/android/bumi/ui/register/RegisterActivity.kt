package com.dicoding.android.bumi.ui.register

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.data.model.RegisterResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivityRegisterBinding
import com.dicoding.android.bumi.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etlName.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.etlEmail.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.etlPassword.error = "Masukkan password"
                }
                else -> {
                    // save data user
                    register()
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register() {
        onLoading(true)
        onLoading(true)
        val client = ApiConfig.getApiService().register(
            binding.etName.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.error == true){
                        Toast.makeText(this@RegisterActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    } else {
                        onLoading(false)
                        // Toast/Alert
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("Sukses!")
                            setMessage("Akun Berhasil dibuat!")
                            setPositiveButton("Ok") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                } else {
                    onLoading(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }

        })
    }

    // Loading
    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }
}