package com.example.android_imperative.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android_imperative.fragments.AllMovies
import com.example.android_imperative.fragments.RecentlyWatched

class FragmentAdapter(private val fragmentManager: FragmentManager, val lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllMovies()
        }
        return RecentlyWatched()
    }
}
