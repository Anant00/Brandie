package com.bradie.app.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bradie.app.adapters.FOLLOWING_FRAGMENT
import com.bradie.app.adapters.TRENDING_FRAGMENT
import com.bradie.app.adapters.ViewPagerAdapter
import com.bradie.app.databinding.FragmentProfileBinding
import com.bradie.app.view.fragments.home.FollowingFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        val adapter = object : ViewPagerAdapter(requireActivity()) {
            override fun createFragment(position: Int): Fragment {
                return setFragments()[position]?.invoke() ?: throw IndexOutOfBoundsException()
            }
        }
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int) = when (position) {
        TRENDING_FRAGMENT -> "Photos"
        FOLLOWING_FRAGMENT -> "Brands"
        else -> ""
    }

    private fun setFragments(): Map<Int, () -> Fragment> {
        return mapOf(
            TRENDING_FRAGMENT to { FollowingFragment() },
            FOLLOWING_FRAGMENT to { FollowingFragment() }
        )
    }
}
