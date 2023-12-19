package com.example.capstone.view.recomendation

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.api.RecomendationRepository
import com.example.capstone.data.api.response.RecomendationFranchisesItem
import com.example.capstone.data.api.retrofit.ApiConfigML
import kotlinx.coroutines.launch
import org.json.JSONObject

class RecomendationViewModel () : ViewModel() {

    private val _recomendations = MutableLiveData<List<RecomendationFranchisesItem>>()
    val recomendations : LiveData<List<RecomendationFranchisesItem>> = _recomendations



    fun getRecomendation(discover : String) {
        viewModelScope.launch {

            val json = JSONObject().apply {
                put("discover", discover)
            }

            try {

                val apiService = ApiConfigML.getApiService()
                val recomendation = apiService.getRecomendation(json.toString())

                Log.d("SUCCESS Recomendation Activity", recomendation.toString())

                _recomendations.value = recomendation.franchises

            } catch (e : Exception) {
                Log.e("Recomendation Activity", e.toString())
            }
        }
    }

    //    fun getRecomendations() {
//        viewModelScope.launch {
//            recomendationRepository.getRecomendation().catch {
//                _recomendations.value = listOf()
//            }.collect {
//                _recomendations.value = it
//            }
//        }
//    }

}