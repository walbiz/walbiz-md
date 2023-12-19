package com.example.capstone.data.api.response

import com.google.gson.annotations.SerializedName

data class NewPhotoResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
