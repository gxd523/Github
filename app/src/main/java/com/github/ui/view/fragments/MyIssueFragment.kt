package com.github.ui.view.fragments

import com.github.common.CommonViewPagerFragment
import com.github.ui.view.config.FragmentPage
import com.github.ui.view.fragments.subfragments.MyIssueListFragment

/**
 * Created by benny on 7/16/17.
 */
class MyIssueFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn() = listOf(
        FragmentPage(MyIssueListFragment(), "My")
    )

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> = listOf(
        FragmentPage(MyIssueListFragment(), "My")
    )
}