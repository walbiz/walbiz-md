package com.example.capstone.data.api

import com.example.capstone.data.api.response.LoginResponse
import com.example.capstone.data.api.response.RegisterResponse
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiService

class UserRepository {
    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun register(name: String, email: String, password: String, verPassword: String): RegisterResponse {
        return apiService.register(name, email, password, verPassword)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }


    // try get session

    // end get sesion
}