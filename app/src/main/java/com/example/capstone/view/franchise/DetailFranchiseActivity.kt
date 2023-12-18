package com.example.capstone.view.franchise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.response.DetailFranchiseResponse
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.database.Wishlist
import com.example.capstone.databinding.ActivityDetailFranchiseBinding
import com.example.capstone.insert.WishlistAddUpdateViewModel
import com.example.capstone.view.wishlist.WishlistViewModelFactory

class DetailFranchiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFranchiseBinding
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var viewModel: DetailFranchiseViewModel
    private var token: String? = null

    // wishlist
    private var isEdit = false
    private var wishlist : Wishlist? = null
    private lateinit var wishlistAddUpdateViewModel: WishlistAddUpdateViewModel
    // end wish list

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
        val name = intent.getStringExtra(EXTRA_NAME)
        val logo = intent.getStringExtra(EXTRA_LOGO)
        val costs = intent.getStringExtra(EXTRA_COSTS)
        val category = intent.getStringExtra(EXTRA_CATEGORY)
        val type = intent.getStringExtra(EXTRA_TYPE)
        detailViewModel.detailFranchise(franchiseRepository.toString(), id.toString())


        val bundle = Bundle()
        bundle.putString(EXTRA_ID, id)


        // wishlist


        Log.d("NAMAA", name.toString())
//        detailViewModel.detailFranchise(franchiseRepository.toString(), name.toString())
//
//        val bundle1 = Bundle()
//        bundle.putString(EXTRA_NAME, name)
        // end wishlist


        detailViewModel.detailFranchise.observe(this) { detailFranchise ->
            setDetailFranchise(detailFranchise)
        }

        // Favorite
        wishlistAddUpdateViewModel = obtainViewModel(this@DetailFranchiseActivity)

        wishlist = intent.getParcelableExtra(EXTRA_WISHLIST)

        if (wishlist != null) {
            isEdit = true
        } else {
            wishlist = Wishlist()
        }

        wishlistAddUpdateViewModel.getAllWishlist(name.toString())
            .observe(this, Observer { wishlistData ->
                if (wishlistData != null) {
                    isEdit = true
                    wishlist = Wishlist(wishlistData.id, wishlistData.costs, wishlistData.name, wishlistData.category, wishlistData.type, wishlistData.logoImageUrl)
                    Log.d("Added to wishlist", "Loged data != null")
                    binding.wishButton.setImageResource(R.drawable.round_favorite_24)
                } else {
                    wishlist = Wishlist()
                    Log.d("Deleted to wishlist", "Loged data null")
                    binding.wishButton.setImageResource(R.drawable.round_favorite_border)
                }
            })

        binding.wishButton.setOnClickListener {

            Log.d("Data Room", name.toString())

            wishlist.let { wishlist ->
                wishlist!!.costs = costs.toString()
                wishlist.name = name.toString()
                wishlist.category = category.toString()
                wishlist.type = type.toString()
                wishlist.logoImageUrl = logo

            }


            if (isEdit) {
                wishlistAddUpdateViewModel.delete(wishlist as Wishlist)
                binding.wishButton.setImageResource(R.drawable.round_favorite_border)
                showToast("Deleted from wishlist")

                isEdit = false
            } else {
                wishlistAddUpdateViewModel.insert(wishlist as Wishlist)
                binding.wishButton.setImageResource(R.drawable.round_favorite_24)
                showToast("Added to wishlist ")

                isEdit =true
            }
        }

        // End Favorite

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


    // Wishlist

    private fun obtainViewModel(activity: AppCompatActivity): WishlistAddUpdateViewModel {
        val factory = WishlistViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(WishlistAddUpdateViewModel::class.java)
    }

    // End Wishlist

    private  fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_WISHLIST = "extra_wishlist"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LOGO = "extra_logo"
        const val EXTRA_COSTS = "extra_costs"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_EST = "extra_est"
        const val EXTRA_OUTLET = "extra_outlet"
        const val EXTRA_PROFIT = "extra_profit"
        const val EXTRA_DURATION = "extra_duration"
    }
}