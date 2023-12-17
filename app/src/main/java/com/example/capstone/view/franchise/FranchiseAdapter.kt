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
import com.example.capstone.data.api.response.FranchiseResponseItem
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.databinding.ItemFranchiseBinding
import com.example.capstone.view.article.ArticleAdapter.Companion.DIFF_CALLBACK

class FranchiseAdapter : ListAdapter<FranchiseResponseItem, FranchiseAdapter.MyViewHolder>(DIFF_CALLBACK) {

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


        fun bind(franchises : FranchiseResponseItem) {
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

                clickedFranchise.startActivity(intent)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FranchiseResponseItem>() {
            override fun areItemsTheSame(oldItem: FranchiseResponseItem, newItem: FranchiseResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FranchiseResponseItem, newItem: FranchiseResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}