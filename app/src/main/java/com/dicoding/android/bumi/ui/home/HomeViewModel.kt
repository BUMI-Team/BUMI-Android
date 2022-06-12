package com.dicoding.android.bumi.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.Repository
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User
import com.dicoding.android.bumi.data.model.UserResponse
import com.dicoding.android.bumi.data.model.VideoResponse
import com.dicoding.android.bumi.data.model.VideoResponseItem

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository: Repository = Repository(application)

    fun getListHomeVideo(genre: String) = mUserRepository.getListHomeVideo(genre)
    fun setVideo(): LiveData<List<VideoResponseItem>> { return mUserRepository.getListVideo() }
}