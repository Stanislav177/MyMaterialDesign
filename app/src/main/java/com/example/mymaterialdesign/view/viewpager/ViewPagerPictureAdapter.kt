package com.example.mymaterialdesign.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

const val TO_DAY = 0
const val YESTERDAY = 1


class ViewPagerPictureAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val listFragments = arrayListOf(PictureToDay(), PictureYesterday())

    override fun getCount() = listFragments.size

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> listFragments[0]
            1 -> listFragments[1]
            else -> listFragments[0]
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            TO_DAY -> "Сегодняшняя"
            YESTERDAY -> "Вчерашняя"
            else -> "NULL"
        }
    }
}