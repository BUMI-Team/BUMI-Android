package com.dicoding.android.bumi.ui.login

import android.content.ContentValues
import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User
import com.dicoding.android.bumi.data.model.LoginResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivityLoginBinding
import com.dicoding.android.bumi.ui.register.RegisterActivity
import com.dicoding.android.bumi.utils.PrefViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
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

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(this, PrefViewModelFactory(LoginPreferences.getInstance(dataStore)))[LoginViewModel::class.java]
    }

    // Login
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
                    login()
//                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        onLoading(true)
        val client = ApiConfig.getApiService().login(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    onLoading(false)
                    loginViewModel.saveUser(User(responseBody.displayName, responseBody.stsTokenManager.accessToken, true))

                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    onLoading(false)
                    Toast.makeText(this@LoginActivity, getString(R.string.login_failed_msg), Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }

    // Loading
    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }
}