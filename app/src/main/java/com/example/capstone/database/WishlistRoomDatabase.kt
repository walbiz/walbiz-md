package com.example.capstone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Wishlist::class], version = 1)

abstract class WishlistRoomDatabase : RoomDatabase() {

    abstract fun wishlistDao() : WishlistDao

    companion object {

        @Volatile
        private var INSTANCE : WishlistRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : WishlistRoomDatabase {
            if (INSTANCE == null) {
                synchronized(WishlistRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WishlistRoomDatabase::class.java, "wishlist_database")
                        .build()
                }
            }
            return INSTANCE as WishlistRoomDatabase
        }
    }

}