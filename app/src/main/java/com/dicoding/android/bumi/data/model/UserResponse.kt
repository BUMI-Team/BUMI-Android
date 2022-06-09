package com.dicoding.android.bumi.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("userRecord")
	val userRecord: UserRecord? = null
)

data class UserRecord(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean? = null,

	@field:SerializedName("metadata")
	val metadata: Metadata? = null,

	@field:SerializedName("providerData")
	val providerData: List<ProviderDataItem?>? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("disabled")
	val disabled: Boolean? = null,

	@field:SerializedName("tokensValidAfterTime")
	val tokensValidAfterTime: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Metadata(

	@field:SerializedName("lastSignInTime")
	val lastSignInTime: String? = null,

	@field:SerializedName("creationTime")
	val creationTime: String? = null
)

data class ProviderDataUserItem(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("providerId")
	val providerId: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
