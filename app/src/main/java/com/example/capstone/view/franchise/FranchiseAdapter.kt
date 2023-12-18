package com.example.capstone.view.franchise

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.data.api.response.FranchisesItem
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.databinding.ItemFranchiseBinding
import com.example.capstone.view.article.ArticleAdapter.Companion.DIFF_CALLBACK

class FranchiseAdapter : PagingDataAdapter<FranchisesItem, FranchiseAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFranchiseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val franchises = getItem(position)
        if (franchises != null) {
            holder.bind(franchises)
        }
    }

    class MyViewHolder (val binding : ItemFranchiseBinding) :RecyclerView.ViewHolder(binding.root) {

        private val itemFranchise : View = binding.itemFranchise

        val franchiseLogo : ImageView = binding.imageFranchise
        val nameFranchise : TextView = binding.nameFranchise
        val modalFranchise : TextView = binding.modalFranchise
        val categoryFranchise : TextView = binding.categoryFranchise


        fun bind(franchises : FranchisesItem) {
            Glide.with(itemView.context)
                .load(franchises.logoImageUrl)
                .into(franchiseLogo)

            nameFranchise.text = franchises.name
            modalFranchise.text = franchises.costs
            categoryFranchise.text = franchises.category


            val clickedFranchise = binding.root.context

            itemView.setOnClickListener {
                val intent = Intent(clickedFranchise, DetailFranchiseActivity::class.java)

                intent.putExtra(DetailFranchiseActivity.EXTRA_ID, franchises.id)
                intent.putExtra(DetailFranchiseActivity.EXTRA_NAME, franchises.name)
                intent.putExtra(DetailFranchiseActivity.EXTRA_COSTS, franchises.costs)
                intent.putExtra(DetailFranchiseActivity.EXTRA_CATEGORY, franchises.category)
                intent.putExtra(DetailFranchiseActivity.EXTRA_LOGO, franchises.logoImageUrl)
                

                clickedFranchise.startActivity(intent)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FranchisesItem>() {
            override fun areItemsTheSame(oldItem: FranchisesItem, newItem: FranchisesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FranchisesItem, newItem: FranchisesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}