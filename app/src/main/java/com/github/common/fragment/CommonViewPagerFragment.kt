package com.github.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.R
import com.github.common.adapter.CommonViewPagerAdapter
import com.github.model.account.AccountManager
import com.github.model.account.OnAccountStateChangeListener
import com.github.network.entities.User
import com.github.ui.main.MainActivity
import com.github.ui.main.ViewPagerFragmentConfig

abstract class CommonViewPagerFragment : Fragment(), ViewPagerFragmentConfig, OnAccountStateChangeListener {
    private val viewPageAdapter by lazy {
        CommonViewPagerAdapter(childFragmentManager)
    }

    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ViewPager(requireContext()).apply {
            id = R.id.viewPager
            adapter = viewPageAdapter
            viewPager = this
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(viewPager)
        viewPageAdapter.pagerDataList.addAll(
            if (AccountManager.isLoggedIn()) {
                getFragmentPagesLoggedIn()
            } else {
                getFragmentPagesNotLoggedIn()
            }
        )
    }

    override fun onLogin(user: User) {
        viewPageAdapter.pagerDataList.clear()
        viewPageAdapter.pagerDataList.addAll(getFragmentPagesLoggedIn())
    }

    override fun onLogout() {
        viewPageAdapter.pagerDataList.clear()
        viewPageAdapter.pagerDataList.addAll(getFragmentPagesNotLoggedIn())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AccountManager.onAccountStateChangeListeners.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }
}