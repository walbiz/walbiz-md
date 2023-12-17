package com.example.capstone.view.franchise

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.api.FranchiseRepository

class FranchiseViewModelFactory(private val franchiseRepository: FranchiseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FranchiseViewModel::class.java)) {
            return FranchiseViewModel(franchiseRepository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }

}