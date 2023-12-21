package com.example.capstone.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.databinding.ActivityLoginBinding
import com.example.capstone.view.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var settingPreferences: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        settingPreferences = SettingPreferences.getInstance(applicationContext)
        val loginViewModelFactory = LoginViewModelFactory(settingPreferences)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]


        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

//            val loadingProgressBar = binding.ProgressBarLogin

            loginButton.isEnabled = false

//            loadingProgressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = loginViewModel.login(email, password)

                    withContext(Dispatchers.Main) {
//                        loadingProgressBar.visibility = View.GONE
                        loginButton.isEnabled = true

                        if (response.message == "Login successful") {
                            val token = response.user?.id
                            if (token != null) {
                                settingPreferences.saveToken(token)
                                Log.i("LoginActivity", "Token berhasil disimpan: $token")

                                val userName = response.user?.name
                                if (userName != null) {
                                    settingPreferences.saveUserName(userName)
                                }

                                val email = response.user?.email
                                if (email != null) {
                                    settingPreferences.saveEmail(email)
                                }

                                val userId = response.user?.id
                                if (userId != null) {
                                    settingPreferences.saveUserId(userId)
                                }

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("TOKEN", token)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.e("LoginActivity", "Token kosong.")
                            }
                        } else {
                            val errorMessage = response.message ?: "Terjadi kesalahan"
                            showErrorDialog(errorMessage)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
//                        loadingProgressBar.visibility = View.GONE
                        loginButton.isEnabled = true

                        val errorMessage = when (e) {
                            is HttpException -> {
                                if (e.code() == 400) {
                                    getString(R.string.error_400)
                                } else if (e.code() == 401) {
                                    getString(R.string.error_401)
                                } else {
                                    "${e.message}"
                                }
                            }
                            else -> "${e.message}"
                        }
                        showErrorDialog(errorMessage)
                        Log.e("LoginActivity", errorMessage)
                    }
                }
            }
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.login_failed))
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}