package com.github.ui.main.repo

import android.os.Bundle
import com.github.common.fragment.CommonViewPagerFragment
import com.github.model.account.AccountManager
import com.github.ui.main.PagerData
import com.github.ui.main.repo.list.RepoListFragment

class RepoFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn(): List<PagerData> {
        return listOf(
            PagerData(RepoListFragment(), "All")
        )
    }

    override fun getFragmentPagesLoggedIn(): List<PagerData> {
        return listOf(
            PagerData(RepoListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RepoListFragment.OPTIONAL_user, AccountManager.currentUser)
                }
            }, "My"),
            PagerData(RepoListFragment(), "All")
        )
    }

}