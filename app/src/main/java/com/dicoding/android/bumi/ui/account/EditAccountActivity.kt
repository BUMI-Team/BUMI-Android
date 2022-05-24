package com.dicoding.android.bumi.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityEditAccountBinding
import com.dicoding.android.bumi.ui.register.RegisterActivity

class EditAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action Bar
        supportActionBar?.title = "Edit Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAction()
    }

    private fun setupAction() {
        binding.btnConfirm.setOnClickListener {
            Toast.makeText(this@EditAccountActivity, "Profil Berhasil di Update", Toast.LENGTH_SHORT).show()
            val mFragmentManager = supportFragmentManager
            val mAccountFragment = AccountFragment()
            val fragment = mFragmentManager.findFragmentByTag(AccountFragment::class.java.simpleName)
            if (fragment !is AccountFragment) {
                Log.d("MyFlexibleFragment", "Fragment Name :" + AccountFragment::class.java.simpleName)
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.account_container, mAccountFragment, AccountFragment::class.java.simpleName)
                    .commit()
            }
        }
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