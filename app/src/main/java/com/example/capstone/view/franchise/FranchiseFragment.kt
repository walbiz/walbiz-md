package com.example.capstone.view.franchise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.FranchiseRepository
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.databinding.FragmentFranchiseBinding
import com.example.capstone.view.article.ArticleViewModel
import com.example.capstone.view.article.ArticleViewModelFactory
import com.example.capstone.view.onBoarding.OnBoardingPageActivity
import com.example.capstone.view.recomendation.RecomendationActivity
import com.example.capstone.view.recomendation.RecomendationFragment

class FranchiseFragment : Fragment() {

    private lateinit var binding : FragmentFranchiseBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var franchiseAdapter: FranchiseAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var franchiseViewModel: FranchiseViewModel

    private var token : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFranchiseBinding.inflate(inflater, container, false)
       return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.franchise)

        binding.progressBar.visibility = View.VISIBLE

        settingPreferences = SettingPreferences.getInstance(requireContext())

        recyclerView = binding.rvFranchise
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        franchiseAdapter = FranchiseAdapter()

        recyclerView.adapter = franchiseAdapter

        val franchiseRepository = FranchiseRepository(ApiConfigML.getApiService(), token ?: "")

        franchiseViewModel =
            ViewModelProvider(this, FranchiseViewModelFactory(franchiseRepository))[FranchiseViewModel::class.java]

        franchiseViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FranchiseViewModel::class.java]

        franchiseViewModel.franchises.observe(viewLifecycleOwner) { franchises ->
            franchiseAdapter.submitData(lifecycle, franchises)

            binding.progressBar.visibility = View.GONE
        }

        setupAction()

    }

    private fun setupAction() {

        binding.btnTryNow.setOnClickListener {
            startActivity(Intent(requireContext(), RecomendationActivity::class.java))
        }

    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}