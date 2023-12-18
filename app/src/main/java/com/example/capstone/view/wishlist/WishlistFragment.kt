package com.example.capstone.view.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.retrofit.ApiConfigML
import com.example.capstone.databinding.FragmentWishlistBinding


class WishlistFragment : Fragment() {

    private var _wishlistFragmentBinding : FragmentWishlistBinding? = null
    private val binding get() = _wishlistFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistAdapter : WishlistAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var wishlistViewModel: WishlistViewModel

    private var token : String? = null


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _wishlistFragmentBinding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        settingPreferences = SettingPreferences.getInstance(requireContext())
//
//        recyclerView = binding.rvWishlist
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//
//        wishlistAdapter = WishlistAdapter()
//        recyclerView.adapter = wishlistAdapter

//        val wishlistRepository = WishlistRepository(ApiConfigML.getApiService(), token ?:"")


        val wishlistViewModel = obtainViewModel(requireActivity())
        wishlistAdapter = WishlistAdapter()

        binding?.rvWishlist?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.rvWishlist?.setHasFixedSize(true)
        binding?.rvWishlist?.adapter = wishlistAdapter

        wishlistViewModel.getAllWishlist().observe(viewLifecycleOwner) { wishlist ->
            if (wishlist != null) {
                wishlistAdapter.setListWishlist(wishlist)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _wishlistFragmentBinding = null
    }

    private fun obtainViewModel(activity : FragmentActivity) : WishlistViewModel {
        val factory = WishlistViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(WishlistViewModel::class.java)
    }

    companion object {

    }
}