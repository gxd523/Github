package com.github.ui.main.people

import android.os.Bundle
import com.github.common.fragment.CommonViewPagerFragment
import com.github.model.account.AccountManager
import com.github.model.people.PeoplePage.Type.*
import com.github.ui.main.PagerData
import com.github.ui.main.people.list.PeopleListFragment

class PeopleFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn(): List<PagerData> {
        return listOf(
            PagerData(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.REQUIRED_type, ALL.name)
                }
            }, "All")
        )
    }

    override fun getFragmentPagesLoggedIn(): List<PagerData> =
        listOf(
            PagerData(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.OPTIONAL_login, AccountManager.currentUser?.login)
                    putString(PeopleListFragment.REQUIRED_type, FOLLOWER.name)
                }
            }, "Followers"),
            PagerData(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.OPTIONAL_login, AccountManager.currentUser!!.login)
                    putString(PeopleListFragment.REQUIRED_type, FOLLOWING.name)
                }
            }, "Following"),
            PagerData(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.REQUIRED_type, ALL.name)
                }
            }, "All")
        )

}