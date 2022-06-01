package com.dicoding.android.bumi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.ui.onboarding.OnboardingData

class OnboardingViewPagerAdapter(private var context: Context, private var onboardingDataList: List<OnboardingData>) : PagerAdapter() {
    override fun getCount(): Int {
        return onboardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view :View = LayoutInflater.from(context).inflate(R.layout.onboarding_screen_layout,null);

        val imageView: ImageView = view.findViewById(R.id.imageView_onboarding)
        val title: TextView = view.findViewById(R.id.title_onboarding)
        val desc: TextView = view.findViewById(R.id.desc_onboarding)

        imageView.setImageResource(onboardingDataList[position].imageUrl)
        title.text = onboardingDataList[position].title
        desc.text = onboardingDataList[position].desc

        container.addView(view)
        return view
    }
}