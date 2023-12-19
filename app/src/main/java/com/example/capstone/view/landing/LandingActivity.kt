package com.example.capstone.view.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.databinding.ActivityLandingBinding
import com.example.capstone.view.login.LoginActivity
import com.example.capstone.view.main.MainActivity
import com.example.capstone.view.onBoarding.OnBoardingPageActivity
import com.example.capstone.view.register.RegisterActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    private lateinit var settingPreferences: SettingPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.welcome)

        settingPreferences = SettingPreferences.getInstance(applicationContext)

        setupAction()

        lifecycleScope.launch {
            val token = settingPreferences.getToken().first()
            if (!token.isNullOrBlank()) {
                val intent = Intent(this@LandingActivity, MainActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, OnBoardingPageActivity::class.java))
        }
    }
}