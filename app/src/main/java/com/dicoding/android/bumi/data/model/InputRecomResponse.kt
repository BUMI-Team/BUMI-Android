package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class InputRecomResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
