package com.example.capstone.view.franchise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.retrofit.ApiConfigML
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFranchiseViewModel : ViewModel() {

    private val _detailFranchise = MutableLiveData<DetailFranchiseResponse>()
    val detailFranchise : LiveData<DetailFranchiseResponse> = _detailFranchise


    fun detailFranchise(token : String, id: String) {
        val client = ApiConfigML.getApiService().getDetailFranchise(token, id)
        client.enqueue(object : Callback<DetailFranchiseResponse> {
            override fun onResponse(
                call: Call<DetailFranchiseResponse>,
                response: Response<DetailFranchiseResponse>
            ) {
                if (response.isSuccessful) {
                    _detailFranchise.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.body()}")
                }
            }

            override fun onFailure(call: Call<DetailFranchiseResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailStoryViewModel"
    }

}