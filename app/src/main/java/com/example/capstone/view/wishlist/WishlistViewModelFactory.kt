package com.example.capstone.view.wishlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.insert.WishlistAddUpdateViewModel

class WishlistViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var INSTANCE : WishlistViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application) : WishlistViewModelFactory {
            if (INSTANCE == null ) {
                synchronized(WishlistViewModelFactory::class.java) {
                    INSTANCE = WishlistViewModelFactory(application)
                }
            }
            return INSTANCE as WishlistViewModelFactory
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            return WishlistViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(WishlistAddUpdateViewModel::class.java)) {
            return WishlistAddUpdateViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown Viewmodel Class : ${modelClass.name}")
    }

}