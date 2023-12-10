package com.example.capstone.data.api.response

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("nodes")
	val nodes: List<Article>,

	@field:SerializedName("totalCount")
	val totalCount: String,

	@field:SerializedName("error")
	val error: Any
)

data class Article(

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
