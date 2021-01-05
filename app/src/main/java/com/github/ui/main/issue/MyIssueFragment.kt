package com.github.ui.main.issue

import com.github.common.fragment.CommonViewPagerFragment
import com.github.ui.main.PagerData
import com.github.ui.main.issue.list.MyIssueListFragment

/**
 * Created by benny on 7/16/17.
 */
class MyIssueFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn() = listOf(
        PagerData(MyIssueListFragment(), "My")
    )

    override fun getFragmentPagesLoggedIn(): List<PagerData> = listOf(
        PagerData(MyIssueListFragment(), "My")
    )
}