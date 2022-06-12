package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(

	@field:SerializedName("VideoResponse")
	val videoResponse: List<VideoResponseItem>
)

data class VideoResponseItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("genre")
	val genre: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("noID")
	val noID: Int
)
