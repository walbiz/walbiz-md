package com.example.capstone.view.login

import androidx.lifecycle.ViewModel
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.UserRepository
import com.example.capstone.data.api.response.LoginResponse

class LoginViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val userRepository = UserRepository()

    suspend fun login(email: String, password: String): LoginResponse {
        return userRepository.login(email, password)
    }
}