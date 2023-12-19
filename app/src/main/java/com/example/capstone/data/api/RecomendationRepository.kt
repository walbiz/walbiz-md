package com.example.capstone.data.api

import androidx.paging.PagingData
import com.example.capstone.data.api.response.RecomendationFranchisesItem
import com.example.capstone.data.api.retrofit.ApiService
import kotlinx.coroutines.flow.flowOf
import java.util.concurrent.Flow

class RecomendationRepository  (private val apiService: ApiService, private val token : String) {

//    fun getRecomendation() : Flow<PagingData<RecomendationFranchisesItem>> {
//        val pagingSourceFactory = {}
//    }

//    suspend fun getRecomendation() : Flow<List<RecomendationFranchisesItem>> {
//        val data = apiService.getRecomendation("Bearer $token")
//
//        return flowOf(
//            data
//        )
//    }

}