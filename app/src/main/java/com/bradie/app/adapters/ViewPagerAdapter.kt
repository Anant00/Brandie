package com.bradie.app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bradie.app.view.fragments.home.FollowingFragment
import com.bradie.app.view.fragments.home.TrendingFragment

const val TRENDING_FRAGMENT = 0
const val FOLLOWING_FRAGMENT = 1

/**
 * A generic and extendable adapter. Default fragments are @class TrendingFragment
 * and @class FollowingFragment.
 *
 * Override the createFragment method to provide set another fragments.
 */
open class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TRENDING_FRAGMENT to { TrendingFragment() },
        FOLLOWING_FRAGMENT to { FollowingFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
