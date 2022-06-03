package com.dicoding.android.bumi.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityConsultationAgendaBinding

class ConsultationAgendaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultationAgendaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationAgendaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onLoading(false)

        // Action Bar
        supportActionBar?.title = "Agenda Konsultasi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Loading
    private fun onLoading(data: Boolean) {
        binding.progressBar.visibility = if (data) View.VISIBLE else View.INVISIBLE
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}