package com.dicoding.android.bumi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.databinding.ActivityMainBinding
import com.dicoding.android.bumi.ui.home.HomeViewModel
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.dicoding.android.bumi.utils.Constants
import com.dicoding.android.bumi.utils.PrefViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        setupViewModel()
    }

    private fun getToken(token: String) {
        binding.apply {
            if (token.isEmpty()) return
            onLoading(true)
//            mainViewModel.setListStory(token)

        }
    }

    private fun setupViewModel() {
        var name: String
//        val rv = RecyclerView(this)
//        setContentView(R.layout.activity_main)

        homeViewModel = ViewModelProvider(this, PrefViewModelFactory(LoginPreferences.getInstance(dataStore)))[HomeViewModel::class.java]
        homeViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                Constants.token = user.token
                name = user.name
//                startActivity(Intent(this, MainActivity::class.java))

                // Test
                binding.tvName.text = user.isLogin.toString()
                Toast.makeText(this@MainActivity, user.isLogin.toString(), Toast.LENGTH_SHORT).show()

                binding.apply {
//                    val listVideo = rv.findViewById<RecyclerView>(R.id.list_training_video)
//                    listVideo.layoutManager = LinearLayoutManager(this@MainActivity)
                    getToken(Constants.token)
                }
//                finish()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }

    // Loading
    private fun onLoading(data: Boolean) {
        val visibilityState = if(data) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = visibilityState
        binding.tvMsg.visibility = visibilityState
    }
}