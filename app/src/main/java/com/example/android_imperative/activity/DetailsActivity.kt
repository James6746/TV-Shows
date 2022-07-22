package com.example.android_imperative.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_imperative.adapter.TVShortAdapter
import com.example.android_imperative.databinding.ActivityDetailsBinding
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {
    private val TAG = DetailsActivity::class.java.simpleName
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiViews()
    }

    private fun intiViews() {
        initObserves()

        binding.rvShorts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        val iv_detail: ImageView = binding.ivDetail
        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras.getString("show_img")
        val show_name = extras.getString("show_name")
        val show_network = extras.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString("iv_movie")
            iv_detail.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(iv_detail)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun refreshAdapter(items: List<String>) {
        val adapter = TVShortAdapter(this, items)
        binding.rvShorts.adapter = adapter
    }

    private fun initObserves() {
//         Retrofit Related
        viewModel.tvShowDetails.observe(this, {
            Logger.d(TAG, it.toString())
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
        })
        viewModel.errorMessage.observe(this, {
            Logger.d(TAG, it.toString())
        })
        viewModel.isLoading.observe(this, Observer {
            Logger.d(TAG, it.toString())
            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })
    }
}