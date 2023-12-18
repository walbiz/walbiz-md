package com.example.capstone.view.wishlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.database.Wishlist

class WishlistViewModel(application: Application) : ViewModel() {

    private val mWishlistRepository : WishlistRepository = WishlistRepository(application)

    fun getAllWishlist() : LiveData<List<Wishlist>> = mWishlistRepository.getWAllishlist()

}