package com.example.capstone.view.franchise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.databinding.ActivityDetailFranchiseBinding

class DetailFranchiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFranchiseBinding
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var viewModel: DetailFranchiseViewModel
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFranchiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingPreferences = SettingPreferences.getInstance(this)


        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailFranchiseViewModel::class.java
        )

        val franchiseRepository = FranchiseRepository(ApiConfigML.getApiService(), token ?: "")

        val id = intent.getStringExtra(EXTRA_ID)
        detailViewModel.detailFranchise(franchiseRepository.toString(), id.toString())


        val bundle = Bundle()
        bundle.putString(EXTRA_ID, id)

        detailViewModel.detailFranchise.observe(this) { detailFranchise ->
            setDetailFranchise(detailFranchise)
        }

//
//        viewModel.detailFranchise(franchiseRepository.toString(), id.toString())
//
//        val bundle = Bundle()
//        bundle.putString(EXTRA_ID, id)
//
//        viewModel.detailFranchise.observe(this) { detail ->
//            detail?.let {
//                setDetailFranchise(it)
//            }
//        }

    }


    private fun setDetailFranchise(franchise : DetailFranchiseResponse) {

        binding.nameFranchise.text = franchise.name
        binding.descriptionFranchise.text = franchise.description
        binding.typeFranchise.text = franchise.type
        binding.categoryFranchise.text = franchise.category
        binding.costFranchise.text = resources.getString(R.string.rp, franchise.costs)
        binding.outletFranchise.text = franchise.totalOutlets
        binding.franchiseCompane.text = franchise.companyName
        binding.website.text = franchise.websiteUrl
        binding.phoneFranchise.text = franchise.phoneNumber
        binding.emailFranchise.text = franchise.emailAddress
        binding.establishedFranchise.text = franchise.yearEstablished
        binding.profitFranchise.text = resources.getString(R.string.rp, franchise.netProfitsPerMonth)
        binding.locationFranchise.text = franchise.companyAddress
        binding.turnInvesment.text = resources.getString(R.string.percent, franchise.returnOfInvestment)

        Glide.with(this)
            .load(franchise.imageUrl)
            .into(binding.imageFranchise)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}