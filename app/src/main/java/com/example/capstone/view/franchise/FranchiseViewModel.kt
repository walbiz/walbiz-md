package com.example.capstone.view.franchise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.FranchisesItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FranchiseViewModel (private val franchiseRepository: FranchiseRepository) : ViewModel() {

    val franchises : LiveData<PagingData<FranchisesItem>> =
        franchiseRepository.getFranchises().cachedIn(viewModelScope).asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

}