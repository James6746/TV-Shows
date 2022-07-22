package com.example.android_imperative.fragments

import android.content.Intent
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.android_imperative.activity.DetailsActivity
import com.example.android_imperative.model.TVShow
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(), sharedImageView, ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }
}