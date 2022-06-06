package com.dicoding.android.bumi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.ui.home.HomeViewModel
import com.dicoding.android.bumi.ui.signin.SigninViewModel

class PrefViewModelFactory(private val pref: LoginPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> { HomeViewModel(pref) as T }
            modelClass.isAssignableFrom(SigninViewModel::class.java) -> { SigninViewModel(pref) as T }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}