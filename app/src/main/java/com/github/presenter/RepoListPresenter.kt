package com.github.presenter

import com.github.model.repo.RepoListPage
import com.github.network.entities.Repository
import com.github.ui.view.common.CommonListPresenter
import com.github.ui.view.fragments.subfragments.RepoListFragment

class RepoListPresenter : CommonListPresenter<Repository, RepoListFragment>() {
    override val listPage by lazy {
        RepoListPage(viewer.user)
    }
}