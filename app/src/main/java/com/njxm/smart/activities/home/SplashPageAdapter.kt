package com.njxm.smart.activities.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.njxm.smart.ui.fragments.HomeFragment

/**
 *
 * @author huangxin
 * @date 2021/7/30
 */
class SplashPageAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
    public var imageUrls: List<String>? = null
        set(value) {
            field = value
        }

    override fun getCount(): Int = imageUrls?.size ?: 0

    override fun getItem(position: Int): Fragment {
        val homeFragment = HomeFragment()
        homeFragment.setImageUrl(imageUrls?.get(position))
        return homeFragment
    }
}