package com.example.capstone.view.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var articleViewModel: ArticleViewModel
    private var token: String? = null
    private var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            ViewModelProvider(this, ArticleViewModelFactory(articleRepository))[ArticleViewModel::class.java]
        articleViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ArticleViewModel::class.java]

        articleViewModel.articleList.observe(viewLifecycleOwner) { storyList ->
            articleAdapter.submitData(lifecycle, storyList)

            Log.d("Article Fragment", articleAdapter.snapshot().items.toString())

        }

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
//            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    val query = searchView.text.toString()
//                    articleViewModel.findDataArticle(query)
//                    searchView.hide()
//                    searchView.hideKeyboard()
//                    return@setOnEditorActionListener true
//                }
//                false
//            }
        }
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed()
//        } else {
//            backPressedTime = System.currentTimeMillis()
//            showExitConfirmationDialog()
//        }
//    }

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
}