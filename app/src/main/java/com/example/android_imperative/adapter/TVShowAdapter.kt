package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.activity.MainActivity
import com.example.android_imperative.model.TVShow


class TVShowAdapter(private var activity: MainActivity, private var items: ArrayList<TVShow>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TVShow = items[position]
        if (holder is TVShowViewHolder) {
            Glide.with(activity).load(tvShow.image_thumbnail_path).placeholder(R.drawable.im_movie)
                .into(holder.iv_movie)
            holder.tv_name.text = tvShow.name
            holder.tv_type.text = tvShow.network

            ViewCompat.setTransitionName(holder.iv_movie, tvShow.name)
            holder.iv_movie.setOnClickListener {
//                activity.callDetailsActivity(tvShow, holder.iv_movie)
            }
        }
    }

    fun updateEmployeeListItems(list: List<TVShow>) {
        val diffCallback = DiffCallBack(this.items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_movie: ImageView
        var tv_name: TextView
        var tv_type: TextView

        init {
            iv_movie = view.findViewById(R.id.iv_movie)
            tv_name = view.findViewById(R.id.tv_name)
            tv_type = view.findViewById(R.id.tv_type)
        }
    }


}