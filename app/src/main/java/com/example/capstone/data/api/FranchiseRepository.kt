package com.example.capstone.data.api

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.response.FranchiseResponseItem
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiService
//import com.example.capstone.view.franchise.FranchisePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FranchiseRepository (private val apiService: ApiService, private val token : String) {

//    fun getFranchises(): Flow<PagingData<FranchiseResponseItem>> {
//        val pagingSourceFactory = {FranchisePagingSource(apiService, token)}
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow
//    }

//    fun getFranchises() : LiveData<PagingData<FranchiseResponseItem>> {
//        return Pager(
//            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
//            pagingSourceFactory = {FranchisePagingSource(apiService, token)}
//        ).liveData
//    }


    suspend fun getFranchises() : Flow<List<FranchiseResponseItem>> {
        val data = apiService.getFranchises("Bearer $token")

        return flowOf(
            data
        )
    }

//    fun getDetailFranchises() : Flow<DetailFranchiseResponse> {
//        val data = apiService.getDetailFranchise("Bearer $token", "")
//
//        return flowOf(data)
//    }

//    suspend fun getDetailFranchiseById(token: String, id: String): DetailFranchiseResponse {
//        return apiService.getDetailFranchise(token, id)
//    }


}