package com.dicoding.android.bumi.ui.recommendation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityBusinessDetailBinding
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

    private fun pilihModalUsaha(): StringBuilder {
        val mikro: RadioButton
        val kecil: RadioButton
        val menengah: RadioButton

        mikro = binding.radioMikro
        kecil = binding.radioKecil
        menengah = binding.radioMenengah
        val modalUsaha = StringBuilder()

        if (mikro.isChecked){
           modalUsaha.append("mikro")
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
        binding.btnDetailKonfirm.setOnClickListener {
            val jenisUsaha = binding.etJenisUsaha.text.toString()
            val hobi = binding.etHobi.text.toString()
            val bidangKeahlian = pilihBidangKeahlian()
            val modalUsaha = pilihModalUsaha()
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
                modalUsaha.isEmpty() -> {
                    Toast.makeText(
                        this@BusinessDetailActivity,
                        "Pilih modal usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        this@BusinessDetailActivity,
                        modalUsaha,
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}