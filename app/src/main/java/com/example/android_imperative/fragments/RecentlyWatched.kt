package com.example.android_imperative.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.FragmentRecentlyWatchedBinding
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.MainViewModel

class RecentlyWatched : BaseFragment() {
    private lateinit var binding: FragmentRecentlyWatchedBinding
    private val TAG = RecentlyWatched::class.java.simpleName
    val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: TVShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentlyWatchedBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        try {
            viewModel.getTVShowsFromDB()
        } catch (e: Exception) {

        }
    }

    private fun initViews() {
        initObserves()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())

        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

        viewModel.getTVShowsFromDB()
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

    private fun initObserves() {
        // Retrofit Related
        viewModel.tvShowsFromDB.observe(requireActivity()) {
            Logger.d(TAG, it!!.toString())
            adapter.updateEmployeeListItems(it)
        }

        viewModel.errorMessage.observe(requireActivity()) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(requireActivity(), Observer {
            Logger.d(TAG, it.toString())
            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })

        // Room Related
//        viewModel.tvShowsFromDB.observe(this, {
//            Logger.d(TAG, it!!.size.toString())
//        })
    }

}