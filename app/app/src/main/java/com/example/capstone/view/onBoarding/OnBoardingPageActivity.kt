package com.example.capstone.view.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone.R
import com.example.capstone.databinding.ActivityOnBoardingPageBinding
import com.example.capstone.view.login.LoginActivity
import com.example.capstone.view.register.RegisterActivity

class OnBoardingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()

    }

    private fun setupAction() {

        binding.button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}