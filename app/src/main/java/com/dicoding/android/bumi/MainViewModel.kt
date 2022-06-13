package com.dicoding.android.bumi

import androidx.lifecycle.*
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User

class MainViewModel(private val pref: LoginPreferences) : ViewModel() {
    // User Token
    fun getUser(): LiveData<User> { return pref.getUser().asLiveData() }
}