package com.dicoding.android.bumi

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.databinding.ActivityMainBinding
import com.dicoding.android.bumi.utils.Constants
import com.dicoding.android.bumi.utils.Constants.token
import com.dicoding.android.bumi.utils.PrefViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bottom Navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_consultation,
                R.id.navigation_favorite,
                R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Home ViewModel
        mainViewModel = ViewModelProvider(
            this,
            PrefViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        // Home ViewModel
        var name: String
        mainViewModel.getUser().observe(this) { user ->
//            if (user.isLogin) {
            token = "Bearer ${user.token}"
            name = user.name
            Constants.EXTRA_UID = user.uid

            binding.apply {
                getToken(token)
            }
        }
    }

    private fun getToken(token: String) {
        binding.apply {
            if (token.isEmpty()) return
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        }
        return super.onOptionsItemSelected(item)
    }
}