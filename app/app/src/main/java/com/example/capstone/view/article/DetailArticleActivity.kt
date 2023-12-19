package com.example.capstone.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.response.Article
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.databinding.ActivityDetailArticleBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var settingPreferences: SettingPreferences
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_article)

        settingPreferences = SettingPreferences.getInstance(this)
        val articleId = intent.getStringExtra("article_id")

        if (articleId != null) {
            loadStoryDetails(articleId)
        } else {
            Log.e("DetailArticleId", "articleId null")
        }
    }
    private fun loadStoryDetails(articleId: String) {
        lifecycleScope.launch {
            try {
                if (token == null) {
                    token = settingPreferences.getToken().first()
                }

                if (token != null) {
                    val apiService = ApiConfig.getApiService()

                    val response = apiService.getArticlesById(articleId)
                    if (response.article != null) {
                        val article = response.article
                        updateUIWithStoryDetails(article)
                    } else {
                        handleEmptyStoryData()
                    }
                } else {
                    handleEmptyToken()
                }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun updateUIWithStoryDetails(article: Article) {
        binding.tvNameArticle.text = article.title
        binding.tvAuthorName.text = article.author
        binding.articleDate.text = article.createdAt
        binding.articleContent.text = article.content
        binding.tvLinkSource.text = article.source

        Glide.with(this@DetailArticleActivity)
            .load(article.imageUrl)
            .into(binding.imgArticle)
    }

    private fun handleEmptyStoryData() {
        Log.e("DetailStoryActivity", "Data cerita kosong")
    }

    private fun handleEmptyToken() {
        Log.e("DetailStoryActivity", "Token kosong")
    }

    private fun handleException(e: Exception) {
        e.printStackTrace()
        showErrorDialogFromException(e)
    }

    private fun showErrorDialogFromException(e: Exception) {
        if (!isFinishing) {
            val errorMessage = when (e) {
                is HttpException -> {
                    if (e.code() == 401) {
                        getString(R.string.unauthorized)
                    } else {
                        "${e.message}"
                    }
                }
                is IOException -> getString(R.string.error_internet)
                else -> "${e.message}"
            }

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}