package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("userCredential")
    val userCredential: UserCredential
)

data class UserCredential(

    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("emailVerified")
    val emailVerified: Boolean,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("isAnonymous")
    val isAnonymous: Boolean,

    @field:SerializedName("stsTokenManager")
    val stsTokenManager: StsTokenManager,

    @field:SerializedName("lastLoginAt")
    val lastLoginAt: String,

    @field:SerializedName("apiKey")
    val apiKey: String,

    @field:SerializedName("providerData")
    val providerData: List<ProviderDataItem?>,

    @field:SerializedName("displayName")
    val displayName: String,

    @field:SerializedName("appName")
    val appName: String,

    @field:SerializedName("email")
    val email: String
)

data class ProviderDataItem(

    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("photoURL")
    val photoURL: Any,

    @field:SerializedName("phoneNumber")
    val phoneNumber: Any,

    @field:SerializedName("providerId")
    val providerId: String,

    @field:SerializedName("displayName")
    val displayName: String,

    @field:SerializedName("email")
    val email: String
)

data class StsTokenManager(

    @field:SerializedName("expirationTime")
    val expirationTime: Long,

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("refreshToken")
    val refreshToken: String
)
