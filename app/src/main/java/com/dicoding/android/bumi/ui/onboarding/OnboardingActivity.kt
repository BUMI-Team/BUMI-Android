package com.dicoding.android.bumi.ui.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.adapter.OnboardingViewPagerAdapter
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.google.android.material.tabs.TabLayout

class OnboardingActivity : AppCompatActivity() {

    var onboardingViewPagerAdapter: OnboardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var tvNext: TextView ?= null
    var position = 0
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (restorePrefData()){
            val i = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(i)
            finish()
        }

        setContentView(R.layout.activity_onboarding)

        tabLayout = findViewById(R.id.tab_indicator)
        tvNext = findViewById(R.id.tv_selanjutnya)

        val onboardingData: MutableList<OnboardingData> = ArrayList()
        onboardingData.add(OnboardingData("Membangun Usaha", "Memberikan rekomendasi usaha yang cocok untuk kamu berdasarkan pengalaman dan kemampuan yang kamu miliki", R.drawable.membangun_usaha))
        onboardingData.add(OnboardingData("Menonton Video", "Kamu bisa menonton berbagai kategori video untuk meningkatkan kemampuan dan pengetahuan tentang UMKM", R.drawable.menonton_video))
        onboardingData.add(OnboardingData("Menjadwalkan Konsultasi", "Konsultasi langsung dengan orang-orang yang ahli dalam bidang UMKM untuk membahas perkembangan usaha kamu", R.drawable.menjadwalkan_konsultasi))

        setOnboardingViewPagerAdapter(onboardingData)

        position = onBoardingViewPager!!.currentItem

        tvNext?.setOnClickListener {
            if(position < onboardingData.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if(position == onboardingData.size){
                savePrefData()
                val i = Intent(applicationContext, WelcomeActivity::class.java)
                startActivity(i)
            }
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onboardingData.size -1) {
                    tvNext!!.text = "Ayo Mulai"
                } else {
                    tvNext!!.text = "Selanjutnya"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        setupView()
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

    private fun setOnboardingViewPagerAdapter(onboardingData: List<OnboardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager);
        onboardingViewPagerAdapter = OnboardingViewPagerAdapter(this, onboardingData)
        onBoardingViewPager!!.adapter = onboardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePrefData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun", false)
    }
}