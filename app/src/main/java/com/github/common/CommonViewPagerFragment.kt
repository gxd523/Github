package com.github.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.github.R
import com.github.model.account.AccountManager
import com.github.model.account.OnAccountStateChangeListener
import com.github.network.entities.User
import com.github.ui.main.MainActivity
import com.github.ui.view.config.ViewPagerFragmentConfig
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.verticalLayout

abstract class CommonViewPagerFragment : Fragment(), ViewPagerFragmentConfig, OnAccountStateChangeListener {
    private val viewPageAdapter by lazy {
        CommonViewPageAdapter(childFragmentManager)
    }

    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                viewPager = viewPager {
                    id = R.id.viewPager
                }
                viewPager.adapter = viewPageAdapter
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(viewPager)
        viewPageAdapter.fragmentPages.addAll(
            if (AccountManager.isLoggedIn()) {
                getFragmentPagesLoggedIn()
            } else {
                getFragmentPagesNotLoggedIn()
            }
        )
    }

    override fun onLogin(user: User) {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesLoggedIn())
    }

    override fun onLogout() {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesNotLoggedIn())
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