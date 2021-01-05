package com.github.ui.view.fragments

import android.os.Bundle
import com.github.common.CommonViewPagerFragment
import com.github.model.account.AccountManager
import com.github.model.people.PeoplePage.Type.*
import com.github.ui.view.config.FragmentPage
import com.github.ui.view.fragments.subfragments.PeopleListFragment

/**
 * Created by benny on 7/16/17.
 */
class PeopleFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(FragmentPage(PeopleListFragment().also {
            it.arguments = Bundle().apply {
                putString(PeopleListFragment.REQUIRED_type, ALL.name)
            }
        }, "All"))
    }

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> =
        listOf(
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.OPTIONAL_login, AccountManager.currentUser?.login)
                    putString(PeopleListFragment.REQUIRED_type, FOLLOWER.name)
                }
            }, "Followers"),
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.OPTIONAL_login, AccountManager.currentUser!!.login)
                    putString(PeopleListFragment.REQUIRED_type, FOLLOWING.name)
                }
            }, "Following"),
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString(PeopleListFragment.REQUIRED_type, ALL.name)
                }
            }, "All")
        )

}