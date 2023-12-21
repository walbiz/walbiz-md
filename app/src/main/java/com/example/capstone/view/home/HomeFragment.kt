package com.example.capstone.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.ArticleRepository
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.view.article.ArticleAdapter
import com.example.capstone.view.article.ArticleViewModel
import com.example.capstone.view.franchise.FranchiseAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var franchiseAdapter: FranchiseAdapter

    private var token : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.home)

        settingPreferences = SettingPreferences.getInstance(requireContext())

        // Try Franchise

        recyclerView = binding.rvFranchise
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        franchiseAdapter = FranchiseAdapter()
        recyclerView.adapter = franchiseAdapter

        val franchiseRepository = FranchiseRepository(ApiConfigML.getApiService(), token ?:"")
        val articleRepository = ArticleRepository(ApiConfig.getApiService(), token ?: "")

//
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(articleRepository, franchiseRepository))[HomeViewModel::class.java]

        homeViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]


//        homeViewModel.getFranchises()
        homeViewModel.franchises.observe(viewLifecycleOwner) { franchises ->

            franchiseAdapter.submitData(lifecycle, franchises)

        }

        // End Franchise

        recyclerView = binding.rvArticle
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

//        val articleRepository = ArticleRepository(ApiConfig.getApiService(), token ?: "")

        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(articleRepository, franchiseRepository))[HomeViewModel::class.java]

        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]

        homeViewModel.articleList.observe(viewLifecycleOwner) { articles ->
            articleAdapter.submitData(lifecycle, articles)
        }



        // WELCOME
        lifecycleScope.launch {
            val userName = settingPreferences.getUserName().first()
            if (!userName.isNullOrBlank()) {

                binding.welcomeUsername.text = "$userName"
            }
        }
        // END WELCOME

    }


    companion object {
    }
}