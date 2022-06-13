package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationResultResponse(

	@field:SerializedName("jenis_usaha")
	val jenisUsaha: String? = null,

	@field:SerializedName("bidang_usaha")
	val bidangUsaha: List<String?>? = null,

	@field:SerializedName("rekomendasi")
	val rekomendasi: List<String?>? = null
)
