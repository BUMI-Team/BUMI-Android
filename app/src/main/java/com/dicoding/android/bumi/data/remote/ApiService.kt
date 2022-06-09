package com.dicoding.android.bumi.data.remote

import com.dicoding.android.bumi.data.local.entity.User
import com.dicoding.android.bumi.data.model.*
import retrofit2.Call
import retrofit2.http.*

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

    // Get User
    @GET("api/user/{uid}")
    fun getUser(
        @Header("Authorization") authToken: String,
        @Path("uid") uid: String
    ): Call<UserResponse>
}
