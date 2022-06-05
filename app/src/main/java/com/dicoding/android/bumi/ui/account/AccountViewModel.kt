package com.dicoding.android.bumi.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import kotlinx.coroutines.launch

class AccountViewModel(private val pref: LoginPreferences) : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text



    // Logout
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}