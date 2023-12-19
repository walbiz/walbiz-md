package com.example.capstone.data.api.response

import com.google.gson.annotations.SerializedName

data class AllFranchiseResponse(

	@field:SerializedName("franchises")
	val franchises: List<FranchisesItem> = emptyList()
)

data class FranchisesItem(

	@field:SerializedName("costs")
	val costs: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("logoImageUrl")
	val logoImageUrl: String? = null
)
