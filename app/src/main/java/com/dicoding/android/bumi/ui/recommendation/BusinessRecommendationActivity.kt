package com.dicoding.android.bumi.ui.recommendation

import android.app.Dialog
import android.content.Context
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
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityBusinessRecommendationBinding
import com.dicoding.android.bumi.databinding.ActivityLoginBinding
import java.lang.StringBuilder

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

    private fun pilihModalUsaha(): StringBuilder {
        val mikro: RadioButton
        val kecil: RadioButton
        val menengah: RadioButton

        mikro = binding.radioMikro
        kecil = binding.radioKecil
        menengah = binding.radioMenengah
        val modalUsaha = StringBuilder()

        if (mikro.isChecked){
            modalUsaha.append("under_50")
        }
        if (kecil.isChecked){
            modalUsaha.append("kecil")
        }
        if (menengah.isChecked){
            modalUsaha.append("menengah")
        }
        return modalUsaha
    }

    private fun setupAction() {
        binding.btnRekomKonfirm.setOnClickListener {
            val pengalamanKerja = binding.etPengalamanKerja.text.toString()
            val hobi = binding.etHobi.text.toString()
            val bidangKeahlian = pilihBidangKeahlian()
            val modalUsaha = pilihModalUsaha()

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
                modalUsaha.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessRecommendationActivity,
                        "Pilih modal usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    saveState()
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
        btnKonfirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        popUpDialog.show()
    }

    private fun saveState(){
        val sharedPreference = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.apply {
            putBoolean("BOOLEAN_KEY_POPUP", true)
        }.apply()
    }
}