package com.github.ui.view.widget

import android.database.DataSetObserver
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.github.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class ActionBarController(val mainActivity: MainActivity) {

    //region tablayout
    private val tabLayout by lazy {
        mainActivity.tabLayout
    }

    class ViewPagerDataSetObserver(val tabLayout: TabLayout) : DataSetObserver() {

        var viewPager: ViewPager? = null
            set(value) {
                viewPager?.adapter?.unregisterDataSetObserver(this)
                value?.adapter?.registerDataSetObserver(this)
                field = value
            }

        override fun onChanged() {
            super.onChanged()
            viewPager?.let { viewPager ->
                if (viewPager.adapter?.count ?: 0 <= 1) {
                    tabLayout.visibility = View.GONE
                } else {
                    tabLayout.visibility = View.VISIBLE
                    tabLayout.tabMode = if (viewPager.adapter?.count ?: 0 > 3) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
                }
            }
        }
    }

    private val dataSetObserver by lazy {
        ViewPagerDataSetObserver(tabLayout)
    }

    fun setupWithViewPager(viewPager: ViewPager?) {
        viewPager?.let(dataSetObserver::viewPager::set) ?: run { tabLayout.visibility = View.GONE }
        tabLayout.setupWithViewPager(viewPager)
    }
}