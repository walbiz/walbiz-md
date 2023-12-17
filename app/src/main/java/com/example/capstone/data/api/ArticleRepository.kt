package com.example.capstone.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiService
import com.example.capstone.view.article.ArticlePagingSource
import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val apiService: ApiService, private val token: String) {
    fun getArticlesPaging(): Flow<PagingData<ListArticleItem>> {
        val pagingSourceFactory = { ArticlePagingSource(apiService, token) }
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}