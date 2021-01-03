package com.github.ui.view.fragments.subfragments

import com.bennyhuo.tieguanyin.annotations.Builder
import com.bennyhuo.tieguanyin.annotations.Optional
import com.github.network.entities.Repository
import com.github.network.entities.User
import com.github.presenter.RepoListPresenter
import com.github.ui.view.common.CommonListFragment

/**
 * Created by benny on 7/16/17.
 */
@Builder
class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {
    @Optional
    var user: User? = null

    override val adapter = RepoListAdapter()
}