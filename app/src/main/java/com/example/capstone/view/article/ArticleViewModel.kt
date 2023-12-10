package com.example.capstone.view.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.response.ListArticleItem

class ArticleViewModel(articleRepository: ArticleRepository) : ViewModel() {
    val articleList: LiveData<PagingData<ListArticleItem>> =
        articleRepository.getArticlesPaging().cachedIn(viewModelScope).asLiveData()
}