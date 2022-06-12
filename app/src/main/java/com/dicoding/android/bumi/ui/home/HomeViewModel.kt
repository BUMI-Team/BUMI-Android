package com.dicoding.android.bumi.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.Repository
import com.dicoding.android.bumi.data.model.ListVideosItem

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository: Repository = Repository(application)

    fun getListHomeVideo(genre: String) = mUserRepository.getListHomeVideo(genre)
    fun setVideo(): LiveData<ArrayList<ListVideosItem>> { return mUserRepository.getListVideo() }
}