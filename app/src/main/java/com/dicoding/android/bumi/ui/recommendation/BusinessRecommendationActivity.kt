package com.dicoding.android.bumi.ui.recommendation

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.data.model.InputRecomResponse
import com.dicoding.android.bumi.data.model.RecommendationResultResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import com.dicoding.android.bumi.databinding.ActivityBusinessRecommendationBinding
import com.dicoding.android.bumi.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private fun pilihBidangKeahlian(): StringBuilder {
        val bidangKeahlian = StringBuilder()

        val kuliner: CheckBox = binding.checkboxKuliner
        val rumah_tangga: CheckBox = binding.checkboxRt
        val kesehatan: CheckBox = binding.checkboxKesehatan

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
        binding.btnRekomKonfirm.setOnClickListener {
            val pengalamanKerja = binding.etPengalamanKerja.text.toString()
            val hobi = binding.etHobi.text.toString()
            val bidangKeahlian = pilihBidangKeahlian()
            val modalUsaha = pilihModalUsaha()
            val listBidangKeahlian: List<String> = bidangKeahlian.split(",").toList()
            val listHobi: List<String> = hobi.split(",").toList()

            when {
                pengalamanKerja.isEmpty() -> {
                    binding.pengalamanKerjaTextInputLayout.error = "Masukkan pengalaman kerja anda"
                }
                hobi.isEmpty() -> {
                    binding.hobiTextInputLayout.error = "Masukkan hobi anda"
                }
                bidangKeahlian.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessRecommendationActivity,
                        "Pilih bidang keahlian",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                modalUsaha!!.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessRecommendationActivity,
                        "Pilih modal usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    postRecom(
                        Constants.token, true, listBidangKeahlian, listHobi,
                        modalUsaha, pengalamanKerja
                    )
                    Toast.makeText(this, Constants.EXTRA_UID, Toast.LENGTH_SHORT).show()
                    saveState()
                    recommResult()
                }
            }
        }
    }

    private fun recommResult() {
        val client = ApiConfig.getApiServiceMainFeature().businessRecomResult(
            Constants.EXTRA_UID
        )
        client.enqueue(object : Callback<RecommendationResultResponse> {
            override fun onResponse(call: Call<RecommendationResultResponse>, response: Response<RecommendationResultResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    onLoading(false)
                    popUpDialog(responseBody.jenisUsaha.toString(),
                        responseBody.bidangUsaha.toString(), responseBody.rekomendasi.toString()
                    )
                } else {
                    onLoading(false)
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<RecommendationResultResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }

        })
    }

    private fun popUpDialog(type: String, category: String, name: String) {
        val popUpDialog = Dialog(this)
        popUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog.setContentView(R.layout.popup_hasil_rekomendasi)

        val tipeUMKM = popUpDialog.findViewById<TextView>(R.id.tvTipeUmkm)
        val kategoriUMKM = popUpDialog.findViewById<TextView>(R.id.tvKategoriUsaha)
        val namaUsaha = popUpDialog.findViewById<TextView>(R.id.tvRekomJenisUsaha)
        tipeUMKM.text = type
        kategoriUMKM.text = category
        namaUsaha.text = name

        Constants.EXTRA_TIPE_UMKM = type
        Constants.EXTRA_KATEGORI_UMKM = category

        val btnKonfirm = popUpDialog.findViewById<Button>(R.id.btn_konfirm_hasil_rekom)
        btnKonfirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        popUpDialog.show()
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
                            this@BusinessRecommendationActivity,
                            responseBody.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onLoading(false)
                        Toast.makeText(
                            this@BusinessRecommendationActivity,
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