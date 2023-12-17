package com.example.capstone.data.api

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.response.FranchisesItem
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiService
import com.example.capstone.view.franchise.FranchisePagingSource
//import com.example.capstone.view.franchise.FranchisePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FranchiseRepository (private val apiService: ApiService, private val token : String) {

    fun getFranchises(): Flow<PagingData<FranchisesItem>> {
        val pagingSourceFactory = { FranchisePagingSource(apiService, token) }
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}