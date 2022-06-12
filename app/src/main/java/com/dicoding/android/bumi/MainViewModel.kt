package com.dicoding.android.bumi

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.Repository
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User
import com.dicoding.android.bumi.data.model.LoginResponse

class MainViewModel(private val pref: LoginPreferences) : ViewModel() {
    // User Token
    fun getUser(): LiveData<User> { return pref.getUser().asLiveData() }
}