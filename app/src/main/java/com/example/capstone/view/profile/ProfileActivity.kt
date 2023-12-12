package com.example.capstone.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.databinding.ActivityMainBinding
import com.example.capstone.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var settingPreferences: SettingPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        settingPreferences = SettingPreferences.getInstance(applicationContext)
    }

    private fun setupAction() {
        binding.settingLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}