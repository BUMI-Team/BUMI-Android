package com.dicoding.android.bumi.ui.account

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.Repository
import com.dicoding.android.bumi.data.model.UserResponse

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val mUserRepository: Repository = Repository(application)

    // Get User
    fun setUser(): LiveData<UserResponse> { return mUserRepository.setUser() }
    fun getUser(token: String) = mUserRepository.getUser(token)

    // Update User
    fun setUpdateUser(token: String, name: String, email: String) = mUserRepository.setUpdateUser(token, name, email)
}