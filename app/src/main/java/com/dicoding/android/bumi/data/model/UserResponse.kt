package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("userRecord")
	val userRecord: UserRecord
)

data class Metadata(

	@field:SerializedName("lastSignInTime")
	val lastSignInTime: String,

	@field:SerializedName("creationTime")
	val creationTime: String
)

data class ProviderDataUserItem(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("photoURL")
	val photoURL: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("providerId")
	val providerId: String,

	@field:SerializedName("email")
	val email: String
)

data class UserRecord(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean,

	@field:SerializedName("photoURL")
	val photoURL: String,

	@field:SerializedName("metadata")
	val metadata: Metadata,

	@field:SerializedName("providerData")
	val providerData: List<ProviderDataUserItem?>,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("disabled")
	val disabled: Boolean,

	@field:SerializedName("tokensValidAfterTime")
	val tokensValidAfterTime: String,

	@field:SerializedName("email")
	val email: String
)
