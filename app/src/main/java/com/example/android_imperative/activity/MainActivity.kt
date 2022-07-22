package com.example.android_imperative.activity

import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.android_imperative.adapter.FragmentAdapter
import com.example.android_imperative.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    lateinit var fragmentAdapter: FragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intiViews()

    }

    private fun intiViews() {
        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = fragmentAdapter

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All Movies"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Recently Viewed"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager2.currentItem = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })


    }
}