package com.example.capstone.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.FranchiseRepository

class HomeViewModelFactory(
    private val articleRepository: ArticleRepository,
    private val franchiseRepository: FranchiseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(articleRepository, franchiseRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}