package com.example.capstone.view.recomendation

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.capstone.data.api.response.RecomendationFranchisesItem
//import com.example.capstone.data.api.retrofit.ApiService
//import com.example.capstone.view.franchise.FranchisePagingSource
//
//class RecomendationPagingSource (private val apiService: ApiService, private val token : String) : PagingSource<Int, RecomendationFranchisesItem>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecomendationFranchisesItem> {
//        return try {
//            val position = params.key ?: RecomendationPagingSource.INITIAL_PAGE_INDEX
//            val response = apiService.getRecomendation("Bearer $token", position, params.loadSize)
//            val data = response.franchises
//
//            LoadResult.Page(
//                data = data,
//                prevKey = if (position == FranchisePagingSource.INITIAL_PAGE_INDEX) null else position - 1,
//                nextKey = if (data.isNullOrEmpty()) null else position + 1
//            )
//        } catch (e : Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, RecomendationFranchisesItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//
//
//
//}