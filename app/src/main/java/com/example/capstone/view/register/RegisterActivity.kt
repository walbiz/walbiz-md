package com.example.capstone.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.ActivityRegisterBinding
import com.example.capstone.view.login.LoginActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val signupButton = binding.buttonRegister
        signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val verPassword = binding.confirmPasswordEditText.text.toString()

//            val loadingProgressBar = binding.ProgressBarSignup

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    runOnUiThread {
//                        loadingProgressBar.visibility = View.VISIBLE
                    }

                    val response = registerViewModel.register(name, email, password, verPassword)
                    runOnUiThread {
//                        loadingProgressBar.visibility = View.GONE

                        if (response.error == null) {
                            val message = response.message ?: "Pendaftaran berhasil"
                            showSuccessDialog(message)
                            Log.d("RegisterActivity", "Pendaftaran berhasil: $message")
                        } else {
                            val errorMessage = response.message ?: "Terjadi kesalahan"
                            showErrorDialog(errorMessage)
                            Log.e("RegisterActivity", "Pendaftaran gagal: $errorMessage")
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
//                        loadingProgressBar.visibility = View.GONE

                        val errorMessage = when (e) {
                            is HttpException -> {
                                if (e.code() == 400) {
                                    getString(R.string.registrasi_duplicate)
                                } else {
                                    "${e.message}"
                                }
                            }
                            else -> "${e.message}"
                        }
                        showErrorDialog(errorMessage)
                        Log.e("RegisterActivity", errorMessage)
                    }
                }
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reg_success))
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }


    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reg_failed))
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}