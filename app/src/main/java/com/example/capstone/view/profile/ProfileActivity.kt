package com.example.capstone.view.profile

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.databinding.ActivityMainBinding
import com.example.capstone.databinding.ActivityProfileBinding
import com.example.capstone.view.landing.LandingActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var logoutButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.setting)

        setupAction()

        settingPreferences = SettingPreferences.getInstance(applicationContext)

        logoutButton = binding.actionLogout
        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout_confirm))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton("OK") { _, _ ->
                    lifecycleScope.launch {
                        settingPreferences.clearDataStore()

                        val intent = Intent(this@ProfileActivity, LandingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton(getString(R.string.logout_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        lifecycleScope.launch {
            val userName = settingPreferences.getUserName().first()
            if (!userName.isNullOrBlank()) {
                binding.nameProfile.text = "$userName"
            }
            val email = settingPreferences.getEmail().first()
            if (!email.isNullOrBlank()) {
                binding.valueEmail.text = "$email"
            }

        }
    }

    private fun setupAction() {
        binding.settingLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}