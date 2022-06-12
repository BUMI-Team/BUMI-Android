package com.dicoding.android.bumi.data

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.bumi.data.model.UserResponse
import com.dicoding.android.bumi.data.model.VideoResponse
import com.dicoding.android.bumi.data.model.VideoResponseItem
import com.dicoding.android.bumi.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(application: Application) {
    private val user = MutableLiveData<UserResponse>()
    private val listVideo = MutableLiveData<List<VideoResponseItem>>()

    // Get User
    fun setUser(): LiveData<UserResponse> {
        return user
    }

    fun getUser(token: String) {
        val client = ApiConfig.getApiService().getUser(token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
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

    // Update User
    fun setUpdateUser(token: String, name: String, email: String) {
        val client = ApiConfig.getApiService().updateUser(token, name, email)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
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

    fun getListHomeVideo(genre: String) {
        ApiConfig.getApiServiceMainFeature().homeVideo(genre).enqueue(object :
            Callback<VideoResponse> {
            override fun onResponse(
                call: Call<VideoResponse>,
                response: Response<VideoResponse>
            ) {
                if (response.isSuccessful) {
                    listVideo.postValue(response.body()?.videoResponse)
                }else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }

        })
    }

    fun getListVideo(): LiveData<List<VideoResponseItem>>{
        return listVideo
    }
}