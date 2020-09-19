package com.bradie.app.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bradie.app.adapters.FOLLOWING_FRAGMENT
import com.bradie.app.adapters.TRENDING_FRAGMENT
import com.bradie.app.adapters.ViewPagerAdapter
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.databinding.FragmentHomeBinding
import com.bradie.app.utils.Status
import com.bradie.app.utils.ViewStatus
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("HOME FRAGMENT")
        setViewPager()
    }
    private fun processData(data: ViewStatus<ImagesModel>) {

        when(data.status) {
            Status.SUCCESS -> {}
            Status.ERROR -> {}
            Status.LOADING -> {}
        }
    }

    private fun setViewPager() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int) = when (position) {
        TRENDING_FRAGMENT -> "Trending"
        FOLLOWING_FRAGMENT -> "Following"
        else -> ""
    }
}