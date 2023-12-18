package com.example.capstone.view.wishlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.database.Wishlist
import com.example.capstone.databinding.ItemFranchiseBinding
import com.example.capstone.helper.WishlistDiffCallBack
import com.example.capstone.view.franchise.DetailFranchiseActivity

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private val listWishlist = ArrayList<Wishlist>()

    fun setListWishlist(lisWishlist : List<Wishlist>) {
        val diffCallBack = WishlistDiffCallBack(this.listWishlist, lisWishlist)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        this.listWishlist.clear()
        this.listWishlist.addAll(listWishlist)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistAdapter.WishlistViewHolder {
        val binding = ItemFranchiseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistAdapter.WishlistViewHolder, position: Int) {
        holder.bind(listWishlist[position])
    }

    override fun getItemCount(): Int {
        return listWishlist.size
    }


    class WishlistViewHolder(val binding : ItemFranchiseBinding) : RecyclerView.ViewHolder(binding.root) {

//        val franchiseLogo : ImageView = binding.imageFranchise
//        val nameFranchise : TextView = binding.nameFranchise
//        val modalFranchise : TextView = binding.modalFranchise
//        val categoryFranchise : TextView = binding.categoryFranchise

        fun bind(wishlist : Wishlist) {
            binding.nameFranchise.text = wishlist.name
            binding.modalFranchise.text = wishlist.costs
            binding.categoryFranchise.text = wishlist.category

            Glide.with(itemView.context)
                .load(wishlist.logoImageUrl)
                .into(binding.imageFranchise)

//            nameFranchise.text = wishlist.name
//            modalFranchise.text = wishlist.costs
//            categoryFranchise.text = wishlist.category

            val clickedFranchise = binding.root.context

            itemView.setOnClickListener {
                val intent = Intent(clickedFranchise, DetailFranchiseActivity::class.java)

                intent.putExtra(DetailFranchiseActivity.EXTRA_ID, wishlist.id)
                intent.putExtra(DetailFranchiseActivity.EXTRA_COSTS, wishlist.costs)
                intent.putExtra(DetailFranchiseActivity.EXTRA_NAME, wishlist.name)
                intent.putExtra(DetailFranchiseActivity.EXTRA_CATEGORY, wishlist.category)
                clickedFranchise.startActivity(intent)
            }
        }

    }

}