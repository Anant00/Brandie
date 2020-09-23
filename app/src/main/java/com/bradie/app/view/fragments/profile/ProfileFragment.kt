package com.bradie.app.view.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bradie.app.R
import com.bradie.app.adapters.FOLLOWING_FRAGMENT
import com.bradie.app.adapters.TRENDING_FRAGMENT
import com.bradie.app.adapters.ViewPagerAdapter
import com.bradie.app.databinding.FragmentProfileBinding
import com.bradie.app.view.fragments.home.FollowingFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

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
        /**
         * Override the generic viewPagerAdapter as it has different fragments that are needed in
         * ProfileActivity.
         */
        val adapter = object : ViewPagerAdapter(requireActivity()) {
            override fun createFragment(position: Int): Fragment {
                return setFragments()[position]?.invoke() ?: throw IndexOutOfBoundsException()
            }
        }
        binding.viewPager.adapter = adapter

        /**
         * `offScreenPageLimit` helps to keep number of fragments in memory that is assigned to it.
         *
         * Note: Use this carefully as it could also slow down app. For example, if there are 3 to 5
         * Fragments in the ViewPager and each of these loads data from internet, then all the
         * fragments would load data at once using a lot of internet and memory resources.
         */
        binding.viewPager.offscreenPageLimit = 3

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            try {
                tab.text = getTabTitle(position)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }.attach()
    }

    /**
     * @throws ArrayIndexOutOfBoundsException if more than 2 fragment are set.
     *
     * Equal number of fragments and tabTitle must be set.
     */
    @Throws(ArrayIndexOutOfBoundsException::class)
    private fun getTabTitle(position: Int) = when (position) {
        TRENDING_FRAGMENT -> getString(R.string.photos)
        FOLLOWING_FRAGMENT -> getString(R.string.brands)
        else -> throw ArrayIndexOutOfBoundsException("Only two fragments are accepted.")
    }

    private fun setFragments(): Map<Int, () -> Fragment> {
        return mapOf(
            TRENDING_FRAGMENT to { FollowingFragment() },
            FOLLOWING_FRAGMENT to { FollowingFragment() }
        )
    }
}
