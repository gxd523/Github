package com.github.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.github.ui.main.PagerData
import com.github.util.ViewPagerAdapterList

class CommonViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val pagerDataList = ViewPagerAdapterList<PagerData>(this)

    override fun getItem(position: Int): Fragment {
        return pagerDataList[position].fragment
    }

    override fun getCount(): Int {
        return pagerDataList.size
    }

    override fun getItemPosition(fragment: Any): Int {
        for ((index, page) in pagerDataList.withIndex()) {
            if (fragment == page.fragment) {
                return index
            }
        }
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pagerDataList[position].title
    }
}