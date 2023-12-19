package com.example.capstone.view.article

import android.icu.text.StringSearch
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiService

class ArticlePagingSource(private val apiService: ApiService, private val token: String, private val articleSearch: String?) : PagingSource<Int, ListArticleItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListArticleItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = if (articleSearch!= null) apiService.getSearchArticlle(articleSearch.toString(), position, params.loadSize)
            else apiService.getArticles("Bearer $token", position, params.loadSize)
            val data = response.nodes

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListArticleItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}