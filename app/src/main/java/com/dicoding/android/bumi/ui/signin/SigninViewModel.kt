package com.dicoding.android.bumi.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User
import kotlinx.coroutines.launch

class SigninViewModel(private val pref: LoginPreferences) : ViewModel() {
    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}