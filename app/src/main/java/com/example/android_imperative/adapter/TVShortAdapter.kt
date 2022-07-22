package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.activity.DetailsActivity
import com.example.android_imperative.databinding.ItemTvShortBinding

class TVShortAdapter(var activity: DetailsActivity, var items: List<String>) :
    RecyclerView.Adapter<TVShortAdapter.TVShortViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShortViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_short, parent, false)
        return TVShortViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShortViewHolder, position: Int) {
        val tvShort = items[position]
        Glide.with(activity).load(tvShort).into(holder.binding.ivShort)

    }

    inner class TVShortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShortBinding.bind(view)
    }
}