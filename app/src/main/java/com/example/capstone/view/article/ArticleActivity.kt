package com.example.capstone.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var articleViewModel: ArticleViewModel
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.article)

        settingPreferences = SettingPreferences.getInstance(this)

        settingPreferences = SettingPreferences.getInstance(applicationContext)

        recyclerView = binding.rvArticle
        recyclerView.layoutManager = LinearLayoutManager(this)

        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        val articleRepository = ArticleRepository(ApiConfig.getApiService())
        articleViewModel = ViewModelProvider(this, ArticleViewModelFactory(articleRepository))[ArticleViewModel::class.java]
        articleViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ArticleViewModel::class.java]

        articleViewModel.articleList.observe(this) { storyList ->
            articleAdapter.submitData(lifecycle, storyList)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            backPressedTime = System.currentTimeMillis()
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("exit comfirm")
            .setMessage(getString(R.string.exit_message))
            .setPositiveButton("OK") { _, _ ->
                super.onBackPressed()
                finishAffinity()
            }
            .setNegativeButton(getString(R.string.logout_cancel), null)
            .show()
    }
}