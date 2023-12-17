package com.example.capstone.view.franchise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.FranchiseResponseItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FranchiseViewModel (private val franchiseRepository: FranchiseRepository) : ViewModel() {

//    val franchises : LiveData<PagingData<FranchiseResponseItem>> =
//        franchiseRepository.getFranchises().cachedIn(viewModelScope).asLiveData()

    private val _franchises = MutableLiveData<List<FranchiseResponseItem>>()
    val franchises : LiveData<List<FranchiseResponseItem>> = _franchises


    fun getFranchises() {
        viewModelScope.launch {
            franchiseRepository.getFranchises().catch {

                _franchises.value = listOf()

            }.collect {

                _franchises.value = it

            }
        }
    }

}