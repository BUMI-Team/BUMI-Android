package com.dicoding.android.bumi.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
) : Parcelable