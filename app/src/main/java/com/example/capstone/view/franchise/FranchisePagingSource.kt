package com.example.capstone.view.franchise

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.capstone.data.api.response.FranchisesItem
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiService
import com.example.capstone.view.article.ArticlePagingSource
import retrofit2.awaitResponse

class FranchisePagingSource (private val apiService: ApiService, private val token : String) : PagingSource<Int, FranchisesItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FranchisesItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getFranchises("Bearer $token", position, params.loadSize)
            val data = response.franchises

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isNullOrEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FranchisesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}