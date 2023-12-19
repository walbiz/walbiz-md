package com.example.capstone.view.wishlist

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.capstone.database.Wishlist
import com.example.capstone.database.WishlistDao
import com.example.capstone.database.WishlistRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WishlistRepository(application: Application) {

    private val mWishlistDao : WishlistDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = WishlistRoomDatabase.getDatabase(application)
        mWishlistDao = db.wishlistDao()
    }

    fun getWAllishlist() : LiveData<List<Wishlist>> = mWishlistDao.getAllWishlist()

    fun getAllWishlistName(name : String) : LiveData<Wishlist> = mWishlistDao.getWishlistsName(name)

    fun insert(wishlist: Wishlist) {
        executorService.execute { mWishlistDao.insert(wishlist) }
    }

    fun delete(wishlist: Wishlist) {
        executorService.execute { mWishlistDao.delete(wishlist) }
    }

    fun update(wishlist: Wishlist) {
        executorService.execute { mWishlistDao.update(wishlist) }
    }

}