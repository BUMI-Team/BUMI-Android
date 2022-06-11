package com.dicoding.android.bumi.ui.home

import androidx.lifecycle.*
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User

class HomeViewModel(private val pref: LoginPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    // User Token
    fun getUser(): LiveData<User> { return pref.getUser().asLiveData() }
}