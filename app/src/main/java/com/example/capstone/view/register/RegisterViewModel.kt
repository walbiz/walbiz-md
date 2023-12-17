package com.example.capstone.view.register

import androidx.lifecycle.ViewModel
import com.example.capstone.data.api.UserRepository
import com.example.capstone.data.api.response.RegisterResponse

class RegisterViewModel : ViewModel() {
        private val userRepository = UserRepository()

        suspend fun register(name: String, email: String, password: String, verPassword: String): RegisterResponse {
            return userRepository.register(name, email, password, verPassword)
        }
}