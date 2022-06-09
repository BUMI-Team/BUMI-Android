package com.dicoding.android.bumi.ui.signin

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
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
import com.dicoding.android.bumi.data.local.sharedpref.SharedPreference
import com.dicoding.android.bumi.data.model.LoginResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivityLoginBinding
import com.dicoding.android.bumi.ui.recommendation.BusinessDetailActivity
import com.dicoding.android.bumi.ui.recommendation.BusinessRecommendationActivity
import com.dicoding.android.bumi.ui.signup.SignupActivity
import com.dicoding.android.bumi.utils.PrefViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var signinViewModel: SigninViewModel

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
        signinViewModel = ViewModelProvider(
            this,
            PrefViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[SigninViewModel::class.java]
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
                    login()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun popUpDialog() {
        val popUpDialog = Dialog(this)
        popUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog.setContentView(R.layout.popup_after_login)

        val btnBelum = popUpDialog.findViewById<Button>(R.id.button_belum)
        val btnSudah = popUpDialog.findViewById<Button>(R.id.button_sudah)
        btnBelum.setOnClickListener {
            val intent = Intent(this, BusinessRecommendationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        btnSudah.setOnClickListener {
            val intent = Intent(this, BusinessDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        popUpDialog.show()

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
                    // DataStore
                    signinViewModel.saveUser(
                        User(
                            responseBody.uid,
                            responseBody.displayName,
                            responseBody.stsTokenManager.accessToken,
                            true
                        )
                    )

                    Toast.makeText(this@SigninActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()

//                    Toast.makeText(this@LoginActivity, responseBody.stsTokenManager.accessToken, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SigninActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

                    popUpDialog()
//                    moveToDetail()
                } else {
                    onLoading(false)
                    Toast.makeText(
                        this@SigninActivity,
                        getString(R.string.login_failed_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@SigninActivity, "Error", Toast.LENGTH_SHORT).show()
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }

//    private fun moveToDetail() {
//        val intent = Intent(this, BusinessDetailActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//        finish()
//    }

    // Loading
    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }
}