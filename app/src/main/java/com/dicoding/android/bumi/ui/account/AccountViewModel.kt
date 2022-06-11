package com.dicoding.android.bumi.ui.account

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.Repository
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.model.LoginResponse
import com.dicoding.android.bumi.data.model.UserResponse
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

//     Logout
//    fun logout() {
//        viewModelScope.launch {
//            pref.logout()
//        }
//    }

    private val mUserRepository: Repository = Repository(application)

    // Detail
    fun getUser(): LiveData<UserResponse> { return mUserRepository.getUser() }
    fun setUser(token: String, uid: String) = mUserRepository.setUser(token, uid)
}