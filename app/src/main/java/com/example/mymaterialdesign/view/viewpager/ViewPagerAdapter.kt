package com.example.mymaterialdesign.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val listFragments = arrayListOf(MarsFragment(), EarthFragment(), SystemFragment())

    override fun getCount() = listFragments.size

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> listFragments[0]
            1 -> listFragments[1]
            2 -> listFragments[2]
            else -> listFragments[0]
        }
    }
}