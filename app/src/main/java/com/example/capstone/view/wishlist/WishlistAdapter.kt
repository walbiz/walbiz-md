package com.example.capstone.view.wishlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.database.Wishlist
import com.example.capstone.databinding.ItemFranchiseBinding
import com.example.capstone.databinding.ItemWishlistBinding
import com.example.capstone.helper.WishlistDiffCallBack
import com.example.capstone.view.franchise.DetailFranchiseActivity
import kotlinx.coroutines.NonDisposableHandle.parent

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private val listWishlist = ArrayList<Wishlist>()

    fun setListWishlist(listWishlist: List<Wishlist>) {
        val diffCallBack = WishlistDiffCallBack(this.listWishlist, listWishlist)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listWishlist.clear()
        this.listWishlist.addAll(listWishlist)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistViewHolder {
        val binding = ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(listWishlist[position])
    }

    override fun getItemCount(): Int {
        return listWishlist.size
    }


    class WishlistViewHolder(val binding : ItemWishlistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wishlist : Wishlist) {
            binding.nameFranchise.text = wishlist.name
            binding.modalFranchise.text = itemView.context.resources.getString(R.string.rp, wishlist.costs)
            binding.categoryFranchise.text = wishlist.category
            binding.typeFranchise.text = wishlist.type

            Glide.with(itemView.context)
                .load(wishlist.logoImageUrl)
                .into(binding.imageFranchise)

            val clickedFranchise = binding.root.context
//
            itemView.setOnClickListener {
                val intent = Intent(clickedFranchise, DetailFranchiseActivity::class.java)

                intent.putExtra(DetailFranchiseActivity.EXTRA_ID, wishlist.idFranchise)

                clickedFranchise.startActivity(intent)
            }

        }

    }

}