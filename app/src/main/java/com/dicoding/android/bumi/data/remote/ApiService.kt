package com.dicoding.android.bumi.data.remote

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
    @GET("api/user")
    fun getUser(
        @Header("Authorization") authToken: String
    ): Call<UserResponse>

    // Update Profile User
    @FormUrlEncoded
    @PATCH("api/user")
    fun updateUser(
        @Header("Authorization") authToken: String,
        @Field("displayName") name: String,
        @Field("email") email: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("api/recommender")
    fun inputRecommendation(
        @Header("Authorization") authToken: String,
        @Field("punya_usaha") statusUsaha :Boolean,
        @Field("bidang_keahlian") listBidangKeahlian :List<String>,
        @Field("hobi") listHobi :List<String>,
        @Field("modal_usaha") modalUsaha :String,
        @Field("nama_usaha") namaUsaha :String
    ): Call<InputRecomResponse>

    @FormUrlEncoded
    @POST("api/v1/genre")
    fun homeVideo(
        @Field("genre") genre :String,
    ): Call<VideoResponse>

    @FormUrlEncoded
    @POST("api/v1/inference")
    fun recommVideo(
        @Field("genre") genre :String,
    ): Call<VideoResponse>

    @FormUrlEncoded
    @POST("api/v1/bRecommendation")
    fun businessRecomResult(
        @Field("user_id") user_id :String,
    ): Call<RecommendationResultResponse>
}
