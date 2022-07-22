package com.example.android_imperative.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.ActivityMainBinding
import com.example.android_imperative.databinding.FragmentAllMoviesBinding
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMovies : BaseFragment() {
    private val TAG = AllMovies::class.java.simpleName
    private lateinit var binding: FragmentAllMoviesBinding
    val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: TVShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMoviesBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        initObserves()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())

        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.tvShowsFromApi.value!!.size - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    viewModel.apiTVShowPopular(nextPage)
                }
            }
        })

        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

        viewModel.apiTVShowPopular(1)
    }
    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter


    }

    private fun initObserves() {
        // Retrofit Related
        viewModel.tvShowsFromApi.observe(requireActivity()) {
            Logger.d(TAG, it!!.toString())
            adapter.updateEmployeeListItems(it)
        }

        viewModel.tvShowDetails.observe(requireActivity()) {
            Logger.d(TAG, it.toString())
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