package com.dicoding.android.bumi.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.data.local.entity.User
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: LoginPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    // User Token
    fun getUser(): LiveData<User> { return pref.getUser().asLiveData() }
}

//class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> { HomeViewModel() as T }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//}