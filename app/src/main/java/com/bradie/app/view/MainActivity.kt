package com.bradie.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bradie.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }
    private fun init() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, homeFragment, getString(R.string.home)).hide(homeFragment)
            add(R.id.container, exploreFragment, getString(R.string.explore)).hide(exploreFragment)

        }.commit()

        initListeners()
    }

    private fun initListeners() {
        supportFragmentManager.beginTransaction()
            .show(homeFragment).commit()
        activeFragment = homeFragment

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    if (activeFragment != homeFragment) {
                        supportFragmentManager.beginTransaction().hide(activeFragment)
                            .show(homeFragment).commit()
                        activeFragment = homeFragment
                        true
                    } else {
                        false
                    }
                }
                
                R.id.navigation_profile -> {
                    if (activeFragment != exploreFragment) {
                        supportFragmentManager.beginTransaction().hide(activeFragment)
                            .show(exploreFragment).commit()
                        activeFragment = exploreFragment
                        true
                    } else {
                        false
                    }
                }
                else -> {
                    false
                }
            }
        }
    }
}