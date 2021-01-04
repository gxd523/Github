package com.github.ui.view.fragments.subfragments

import com.github.network.entities.Repository
import com.github.network.entities.User
import com.github.presenter.RepoListPresenter
import com.github.ui.view.common.CommonListFragment

class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {
    companion object {
        const val OPTIONAL_user = "user"
    }

    var user: User? = null

    override val adapter = RepoListAdapter()
}