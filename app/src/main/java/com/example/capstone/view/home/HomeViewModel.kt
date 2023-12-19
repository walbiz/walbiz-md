package com.example.capstone.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.FranchisesItem
import com.example.capstone.data.api.response.ListArticleItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    articleRepository: ArticleRepository,
    private val franchiseRepository: FranchiseRepository
) : ViewModel() {

    val articleList : LiveData<PagingData<ListArticleItem>> =
        articleRepository.getArticlesPaging().cachedIn(viewModelScope).asLiveData()

//    private val _franchises = MutableLiveData<List<FranchiseResponseItem>>()
//    val franchises : LiveData<List<FranchiseResponseItem>> = _franchises

    val franchises : LiveData<PagingData<FranchisesItem>> =
        franchiseRepository.getFranchises().cachedIn(viewModelScope).asLiveData()

//    fun getFranchises() {
//        viewModelScope.launch {
//            franchiseRepository.getFranchises().catch {
//
//                _franchises.value = listOf()
//
//            }.collect {
//
//                _franchises.value = it
//
//            }
//        }
//    }

}