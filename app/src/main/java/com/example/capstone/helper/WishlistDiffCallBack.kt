package com.example.capstone.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.capstone.database.Wishlist

class WishlistDiffCallBack (private val oldWishlist : List<Wishlist>, private val newWishlist : List<Wishlist>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldWishlist.size

    override fun getNewListSize(): Int = newWishlist.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWishlist[oldItemPosition].id == newWishlist[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldWishlist = oldWishlist[oldItemPosition]
        val newWishlist = newWishlist[newItemPosition]
        return oldWishlist.costs == newWishlist.costs &&
                oldWishlist.name == newWishlist.name &&
                oldWishlist.category == newWishlist.category
                oldWishlist.logoImageUrl== newWishlist.logoImageUrl

    }

}