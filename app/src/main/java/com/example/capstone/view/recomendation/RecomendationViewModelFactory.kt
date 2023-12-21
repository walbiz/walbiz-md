package com.example.capstone.view.recomendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecomendationViewModelFactory () : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)) {
            return RecomendationViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}