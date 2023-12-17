package com.example.capstone.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(wishlist: Wishlist)

    @Update
    fun update(wishlist: Wishlist)

    @Delete
    fun delete(wishlist: Wishlist)

    @Query("SELECT * from wishlist ORDER BY id ASC")
    fun getAllWishlist() : LiveData<List<Wishlist>>

    @Query("SELECT * from wishlist WHERE name = :name")
    fun getWishlistsName(name : String) : LiveData<Wishlist>

}