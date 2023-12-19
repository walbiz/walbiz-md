package com.example.capstone.view.recomendation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.data.api.response.FranchisesItem
import com.example.capstone.data.api.response.RecomendationFranchisesItem
import com.example.capstone.databinding.ItemFranchiseBinding
import com.example.capstone.view.franchise.DetailFranchiseActivity

class RecomendationAdapter : ListAdapter<RecomendationFranchisesItem, RecomendationAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecomendationAdapter.MyViewHolder {
        val binding = ItemFranchiseBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomendationAdapter.MyViewHolder, position: Int) {
        val recomendation = getItem(position)
        if (recomendation != null) {
            holder.bind(recomendation)
        }
    }

    class MyViewHolder(val binding : ItemFranchiseBinding) : RecyclerView.ViewHolder(binding.root) {

        val franchiseLogo : ImageView = binding.imageFranchise
        val nameFranchise : TextView = binding.nameFranchise
        val modalFranchise : TextView = binding.modalFranchise
        val categoryFranchise : TextView = binding.categoryFranchise

        fun bind(franchises : RecomendationFranchisesItem) {
            Glide.with(itemView.context)
                .load(franchises.logoImageUrl)
                .into(franchiseLogo)

            nameFranchise.text = franchises.name
            modalFranchise.text = itemView.context.resources.getString(R.string.rp, franchises.costs)
            categoryFranchise.text = franchises.category


            val clickedFranchise = binding.root.context

            itemView.setOnClickListener {
                val intent = Intent(clickedFranchise, DetailFranchiseActivity::class.java)

                intent.putExtra(DetailFranchiseActivity.EXTRA_ID, franchises.id)
                intent.putExtra(DetailFranchiseActivity.EXTRA_NAME, franchises.name)
                intent.putExtra(DetailFranchiseActivity.EXTRA_COSTS, franchises.costs)
                intent.putExtra(DetailFranchiseActivity.EXTRA_TYPE, franchises.type)
                intent.putExtra(DetailFranchiseActivity.EXTRA_CATEGORY, franchises.category)
                intent.putExtra(DetailFranchiseActivity.EXTRA_LOGO, franchises.logoImageUrl)


                clickedFranchise.startActivity(intent)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<RecomendationFranchisesItem>() {
            override fun areItemsTheSame(
                oldItem: RecomendationFranchisesItem,
                newItem: RecomendationFranchisesItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecomendationFranchisesItem,
                newItem: RecomendationFranchisesItem
            ): Boolean {
                return  oldItem == newItem
            }

        }
    }

}