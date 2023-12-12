package com.example.capstone.view.article

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.databinding.ItemRowArticleBinding

class ArticleAdapter : PagingDataAdapter<ListArticleItem, ArticleAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
    class ListViewHolder(binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        private val cardImg: View = binding.cardView
        private val articleImg: ImageView = binding.imgArticle
        private val titleArticle: TextView = binding.tvTitleArticle
        private val descArticle: TextView = binding.tvDesc

        fun bind(article: ListArticleItem) {
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .into(articleImg)
            titleArticle.text = article.title
            descArticle.text = article.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                intent.putExtra("article_id", article.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(cardImg, "card_photo"),
                        Pair(articleImg, "article_photo"),
                        Pair(titleArticle, "title"),
                        Pair(descArticle, "desc")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListArticleItem>() {
            override fun areItemsTheSame(oldItem: ListArticleItem, newItem: ListArticleItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListArticleItem, newItem: ListArticleItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}