package com.dicoding.android.bumi.data

import android.app.Application
import android.content.ContentValues
import android.content.SharedPreferences
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.bumi.data.model.LoginResponse
import com.dicoding.android.bumi.data.model.UserResponse
import com.dicoding.android.bumi.data.remote.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(application: Application) {
    private val user = MutableLiveData<UserResponse>()

    // User User
    fun getUser(): LiveData<UserResponse> { return user }
    fun setUser(token: String, uid: String) {
        val client = ApiConfig.getApiService().getUser(token, uid)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                    Log.e(ContentValues.TAG, "Testtttttt: ${response.message()}")
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }
}