package com.example.capstone.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Wishlist(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "costs")
    var costs : String = "",

    @ColumnInfo(name = "name")
    var name : String = "",

    @ColumnInfo(name = "category")
    var category : String = "",

    @ColumnInfo(name = "logoImageUrl")
    var logoImageUrl : String? = null,

) : Parcelable