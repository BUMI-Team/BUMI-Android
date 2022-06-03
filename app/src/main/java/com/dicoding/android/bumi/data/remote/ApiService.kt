package com.dicoding.android.bumi.data.remote

import com.dicoding.android.bumi.data.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    // Register
    @FormUrlEncoded
    @POST("api/auth/signup")
    fun register(
        @Field("displayName") name :String,
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<RegisterResponse>

    // Login
    @FormUrlEncoded
    @POST("api/auth/signin")
    fun login(
        @Field("email") email :String,
        @Field("password") password :String
    ): Call<LoginResponse>
}