package com.example.capstone.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone.database.Wishlist
import com.example.capstone.view.wishlist.WishlistRepository

class WishlistAddUpdateViewModel(application: Application) : ViewModel() {

    private val mWishlistRepository : WishlistRepository = WishlistRepository(application)

    fun getAllWishlist(name : String) : LiveData<Wishlist> = mWishlistRepository.getAllWishlistName(name)

    fun insert(wishlist : Wishlist) {
        mWishlistRepository.insert(wishlist)
    }

    fun update(wishlist: Wishlist) {
        mWishlistRepository.update(wishlist)
    }

    fun delete(wishlist: Wishlist) {
        mWishlistRepository.delete(wishlist)
    }

}