package com.github.presenter

import com.github.common.CommonListPresenter
import com.github.model.repo.RepoListPage
import com.github.network.entities.Repository
import com.github.ui.main.repo.list.RepoListFragment

class RepoListPresenter : CommonListPresenter<Repository, RepoListFragment>() {
    override val listPage by lazy {
        RepoListPage(viewer.user)
    }
}