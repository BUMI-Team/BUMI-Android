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
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.*
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
import com.dicoding.android.bumi.data.model.LoginResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivitySigninBinding
import com.dicoding.android.bumi.ui.recommendation.BusinessDetailActivity
import com.dicoding.android.bumi.ui.recommendation.BusinessRecommendationActivity
import com.dicoding.android.bumi.ui.signup.SignupActivity
import com.dicoding.android.bumi.utils.Constants
import com.dicoding.android.bumi.utils.PrefViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var signinViewModel: SigninViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
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
                else -> {
                    login()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
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
                    Constants.token = "Bearer ${responseBody.userCredential.stsTokenManager.accessToken}"
                    onLoading(false)
                    Constants.EXTRA_UID = responseBody.userCredential.uid
                    // DataStore
                    signinViewModel.saveUser(
                        User(
                            responseBody.userCredential.uid,
                            responseBody.userCredential.displayName,
                            responseBody.userCredential.stsTokenManager.accessToken,
                            true
                        )
                    )
                    saveState()
                    Toast.makeText(this@SigninActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    
                    val sharedPreference = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val savedPopUpState = sharedPreference.getBoolean("BOOLEAN_KEY_POPUP", false)
                    if (!savedPopUpState) {
                        popUpDialog()
                    } else {
                        val intent = Intent(this@SigninActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
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
    
    private fun popUpDialog() {
        val popUpDialog = Dialog(this)
        popUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog.setContentView(R.layout.popup_after_login)
        popUpDialog.show()

        val btnBelum = popUpDialog.findViewById<Button>(R.id.button_belum)
        val btnSudah = popUpDialog.findViewById<Button>(R.id.button_sudah)
        btnBelum.setOnClickListener {
            popUpDialog.dismiss()
            val intent = Intent(this, BusinessRecommendationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        btnSudah.setOnClickListener {
            popUpDialog.dismiss()
            val intent = Intent(this, BusinessDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    // Loading
    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }

    private fun saveState() {
        val sharedPreference = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.apply {
            putString("STRING_KEY", "login")
        }.apply()
    }
}