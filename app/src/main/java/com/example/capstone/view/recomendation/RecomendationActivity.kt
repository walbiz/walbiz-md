package com.example.capstone.view.recomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.databinding.ActivityRecomendationBinding
import com.example.capstone.view.franchise.DetailFranchiseViewModel
import com.example.capstone.view.franchise.FranchiseViewModel
import com.example.capstone.view.franchise.FranchiseViewModelFactory
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject

class RecomendationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecomendationBinding
    private lateinit var viewModel: RecomendationViewModel
    private lateinit var settingPreferences: SettingPreferences
//    private lateinit var viewmodel :

    private var token : String? = null

    // view model
//    private val viewModel by viewModels<RecomendationViewModel>()
    private val adapter by lazy { RecomendationAdapter() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel =
            ViewModelProvider(this, RecomendationViewModelFactory())[RecomendationViewModel::class.java]


        setupAction()

    }

    private fun setupAction() {
        binding.btnSubmit.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE
            binding.imgDataEmpty.visibility = View.GONE

            val discover = binding.recomendationForm.text.toString()

            binding.rvFranchise.adapter = adapter
            binding.rvFranchise.layoutManager = GridLayoutManager(this, 2)

            viewModel.getRecomendation(discover)
            viewModel.recomendations.observe(this) {
                adapter.submitList(it)

                binding.progressBar.visibility = View.GONE

                if (!it.isNullOrEmpty()) {
                    binding.imgDataEmpty.visibility = View.GONE
                } else {
                    binding.imgDataEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}