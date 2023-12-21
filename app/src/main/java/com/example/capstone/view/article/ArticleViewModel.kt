package com.example.capstone.view.article

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.response.AllArticleResponse
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _article = MutableLiveData<PagingData<ListArticleItem>>()
    val article: LiveData<PagingData<ListArticleItem>> = _article

    val articleList: LiveData<PagingData<ListArticleItem>> =
        articleRepository.getArticlesPaging().cachedIn(viewModelScope).asLiveData()

    fun findDataArticle(searchArticle: String) {
        viewModelScope.launch {
            val article = articleRepository.getArticleSearchPaging(searchArticle)
            article.collect{
                _article.value=it
            }
//            enqueueCall(article)
        }
    }
}
