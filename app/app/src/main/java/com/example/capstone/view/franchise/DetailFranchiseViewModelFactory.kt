package com.example.capstone.view.franchise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.api.FranchiseRepository

class DetailFranchiseViewModelFactory(private val franchiseRepository: FranchiseRepository) : ViewModelProvider.Factory {

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DetailFranchiseViewModel::class.java)) {
//            return DetailFranchiseViewModel(franchiseRepository) as T
//        }
//
//        throw IllegalArgumentException("Unknown View Model Class")
//
//    }

}
