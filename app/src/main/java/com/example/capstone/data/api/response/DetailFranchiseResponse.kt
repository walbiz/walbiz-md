package com.example.capstone.data.api.response

import com.google.gson.annotations.SerializedName

data class DetailFranchiseResponse(

	@field:SerializedName("costs")
	val costs: String? = null,

	@field:SerializedName("yearEstablished")
	val yearEstablished: String? = null,

	@field:SerializedName("totalOutlets")
	val totalOutlets: String? = null,

	@field:SerializedName("companyName")
	val companyName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("netProfitsPerMonth")
	val netProfitsPerMonth: String? = null,

	@field:SerializedName("logoImageUrl")
	val logoImageUrl: String? = null,

	@field:SerializedName("licenseDurationInYears")
	val licenseDurationInYears: String? = null,

	@field:SerializedName("emailAddress")
	val emailAddress: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("returnOfInvestment")
	val returnOfInvestment: String? = null,

	@field:SerializedName("websiteUrl")
	val websiteUrl: String? = null,

	@field:SerializedName("companyAddress")
	val companyAddress: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("royaltyFeesPerMonth")
	val royaltyFeesPerMonth: String? = null
)
