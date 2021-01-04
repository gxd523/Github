package com.github.ui.view.fragments.subfragments

import android.os.Bundle
import com.github.common.CommonListFragment
import com.github.network.entities.Repository
import com.github.network.entities.User
import com.github.presenter.RepoListPresenter

class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {
    companion object {
        const val OPTIONAL_user = "user"
    }

    var user: User? = null

    override val adapter = RepoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable(OPTIONAL_user)
    }
}