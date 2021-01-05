package com.github.ui.main.repo

import android.os.Bundle
import com.github.common.fragment.CommonViewPagerFragment
import com.github.model.account.AccountManager
import com.github.ui.main.FragmentPage
import com.github.ui.main.repo.list.RepoListFragment

class RepoFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(RepoListFragment(), "All")
        )
    }

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(RepoListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RepoListFragment.OPTIONAL_user, AccountManager.currentUser)
                }
            }, "My"),
            FragmentPage(RepoListFragment(), "All")
        )
    }

}