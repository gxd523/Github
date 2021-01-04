package com.github.ui.view.fragments

import android.os.Bundle
import com.github.model.account.AccountManager
import com.github.ui.view.common.CommonViewPagerFragment
import com.github.ui.view.config.FragmentPage
import com.github.ui.view.fragments.subfragments.RepoListFragment

class RepoFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(FragmentPage(RepoListFragment(), "All"))
    }

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(RepoListFragment().apply {
                arguments = Bundle().apply { putParcelable(RepoListFragment.OPTIONAL_user, AccountManager.currentUser) }
            }, "My"),
            FragmentPage(RepoListFragment(), "All")
        )
    }

}