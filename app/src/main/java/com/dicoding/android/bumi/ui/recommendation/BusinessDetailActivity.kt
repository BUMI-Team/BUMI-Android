package com.dicoding.android.bumi.ui.recommendation

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
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.data.model.InputRecomResponse
import com.dicoding.android.bumi.data.model.RegisterResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivityBusinessDetailBinding
import com.dicoding.android.bumi.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

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

    private fun pilihBidangKeahlian(): StringBuilder {
        val kuliner: CheckBox
        val rumah_tangga: CheckBox
        val kesehatan: CheckBox
        val bidangKeahlian = StringBuilder()

        kuliner = binding.checkboxKuliner
        rumah_tangga = binding.checkboxRt
        kesehatan = binding.checkboxKesehatan

        when {
            kuliner.isChecked && rumah_tangga.isChecked && kesehatan.isChecked -> {
                bidangKeahlian.append(kuliner.text.toString() + "," + rumah_tangga.text.toString() + "," + kesehatan.text.toString())
            }
            kuliner.isChecked && rumah_tangga.isChecked -> {
                bidangKeahlian.append(kuliner.text.toString() + "," + rumah_tangga.text.toString())
            }
            kuliner.isChecked && kesehatan.isChecked -> {
                bidangKeahlian.append(kuliner.text.toString() + "," + kesehatan.text.toString())
            }
            rumah_tangga.isChecked && kesehatan.isChecked -> {
                bidangKeahlian.append(rumah_tangga.text.toString() + "," + kesehatan.text.toString())
            }
            rumah_tangga.isChecked -> {
                bidangKeahlian.append(rumah_tangga.text.toString())
            }
            kesehatan.isChecked -> {
                bidangKeahlian.append(kesehatan.text.toString())
            }
            kuliner.isChecked -> {
                bidangKeahlian.append(kuliner.text.toString())
            }
            else -> {
                Toast.makeText(this, "Pilih bidang keahlian", Toast.LENGTH_SHORT).show()
            }
        }
        return bidangKeahlian
    }

    private fun pilihModalUsaha(): String? {
        val mikro: RadioButton = binding.radioMikro
        val kecil: RadioButton = binding.radioKecil
        val menengah: RadioButton = binding.radioMenengah
        var modalUsaha: String? = null

        if (mikro.isChecked) {
            modalUsaha = "under_50"
        } else if (kecil.isChecked) {
            modalUsaha = "between_50_and_100"
        } else if (menengah.isChecked) {
            modalUsaha = "above_100"
        }
        return modalUsaha
    }

    private fun setupAction() {
        binding.btnDetailKonfirm.setOnClickListener {
            val jenisUsaha = binding.etJenisUsaha.text.toString()
            val hobi = binding.etHobi.text.toString()
            val bidangKeahlian = pilihBidangKeahlian()
            val modalUsaha = pilihModalUsaha()
            val listBidangKeahlian: List<String> = bidangKeahlian.split(",").toList()
            val listHobi: List<String> = hobi.split(",").toList()
            when {
                jenisUsaha.isEmpty() -> {
                    binding.jenisUsahaTextInputLayout.error = "Masukkan jenis usaha anda"
                }
                hobi.isEmpty() -> {
                    binding.hobiTextInputLayout.error = "Masukkan hobi anda"
                }
                bidangKeahlian.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessDetailActivity,
                        "Pilih bidang keahlian",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                modalUsaha!!.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessDetailActivity,
                        "Pilih modal usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    postRecom(
                        Constants.token, true, listBidangKeahlian, listHobi,
                        modalUsaha, jenisUsaha
                    )
                    saveState()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    private fun saveState() {
        val sharedPreference = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.apply {
            putBoolean("BOOLEAN_KEY_POPUP", true)
        }.apply()
    }

    private fun postRecom(
        token: String,
        punyaUsaha: Boolean,
        bidangKeahlian: List<String>,
        hobi: List<String>,
        modalUsaha: String,
        nama_usaha: String
    ) {
        onLoading(true)
        onLoading(true)
        val client = ApiConfig.getApiService().inputRecommendation(
            token,
            punyaUsaha,
            bidangKeahlian,
            hobi,
            modalUsaha,
            nama_usaha
        )
        client.enqueue(object : Callback<InputRecomResponse> {
            override fun onResponse(
                call: Call<InputRecomResponse>,
                response: Response<InputRecomResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.code != 200) {
                        Toast.makeText(
                            this@BusinessDetailActivity,
                            responseBody.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onLoading(false)
                        Toast.makeText(
                            this@BusinessDetailActivity,
                           "Sukses",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    onLoading(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<InputRecomResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }

        })
    }

    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }
}