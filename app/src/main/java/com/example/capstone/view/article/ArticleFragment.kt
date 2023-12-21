package com.example.capstone.view.article

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.response.ListArticleItem
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.databinding.FragmentArticleBinding
import kotlin.math.log

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var searchArticleAdapter: SearchArticleAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var articleViewModel: ArticleViewModel
    private var token: String? = null
    private var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.article)

        settingPreferences = SettingPreferences.getInstance(requireContext())

        recyclerView = binding.rvArticle
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        val articleRepository = ArticleRepository(ApiConfig.getApiService(), token ?: "")
        articleViewModel =
            ViewModelProvider(
                this,
                ArticleViewModelFactory(articleRepository)
            )[ArticleViewModel::class.java]
        articleViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[ArticleViewModel::class.java]

        articleViewModel.articleList.observe(viewLifecycleOwner) { articleList ->
            articleAdapter.submitData(lifecycle, articleList)

            Log.d("Article Fragment", articleAdapter.snapshot().items.toString())

        }
        binding.rvSearchArticle.layoutManager = LinearLayoutManager(requireContext())
        searchArticleAdapter = SearchArticleAdapter()
        binding.rvSearchArticle.adapter = searchArticleAdapter


        binding.apply {
            searchBar.setOnClickListener {
                Toast.makeText(requireContext(), "Halo", Toast.LENGTH_SHORT).show()
                searchArticleAdapter.submitData(lifecycle, PagingData.empty())
            }
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString()
//                    Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                    articleViewModel.findDataArticle(query)
//                    searchView.hide()
                    searchView.hideKeyboard()

                    articleViewModel.article.observe(viewLifecycleOwner) { article ->
                        Log.d("Article Search", article.toString())
                        searchArticleAdapter.submitData(lifecycle, article)
                        searchArticleAdapter.setOnItemClick(object:SearchArticleAdapter.OnItemClickListener{
                            override fun onItemClicked(data: ListArticleItem) {
                                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                                intent.putExtra("article_id", data.id)

                                startActivity(intent)
                            }
                        })

                        Log.d("Article Fragment", articleAdapter.snapshot().items.toString())

                    }

                    return@setOnEditorActionListener true
                }
                false
            }

        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("exit comfirm")
            .setMessage(getString(R.string.exit_message))
            .setPositiveButton("OK") { _, _ ->
                requireActivity().onBackPressed()
                requireActivity().finishAffinity()
            }
            .setNegativeButton(getString(R.string.logout_cancel), null)
            .show()
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}