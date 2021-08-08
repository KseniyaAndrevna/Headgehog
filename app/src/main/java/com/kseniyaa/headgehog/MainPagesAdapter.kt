package com.kseniyaa.headgehog

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kseniyaa.headgehog.screens.InfoFragment
import com.kseniyaa.headgehog.screens.ItemsFragment


class MainPagesAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {

            PAGE_JOKES -> ItemsFragment()

            PAGE_INFO -> InfoFragment()

            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    companion object {
        const val PAGE_JOKES = 0
        const val PAGE_INFO = 1
    }
}



