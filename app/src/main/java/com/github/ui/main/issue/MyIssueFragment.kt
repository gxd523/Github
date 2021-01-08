package com.github.ui.main.issue

import com.github.common.fragment.CommonViewPagerFragment
import com.github.ui.main.PagerData
import com.github.ui.main.issue.list.MyIssueListFragment

class MyIssueFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn() = listOf(
        PagerData(MyIssueListFragment(), "My")
    )

    override fun getFragmentPagesLoggedIn(): List<PagerData> = listOf(
        PagerData(MyIssueListFragment(), "My")
    )
}