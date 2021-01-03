package com.github.model.repo

import com.github.model.page.ListPage
import com.github.network.entities.Repository
import com.github.network.entities.User
import com.github.network.services.RepositoryService
import com.github.util.format
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable
import java.util.*

class RepoListPage(private val owner: User?) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
        return if (owner == null) {
            RepositoryService.repositoryListOfSearch(page, "pushed:<" + Date().format("yyyy-MM-dd")).map { it.paging }
        } else {
            RepositoryService.repositoryListOfUser(owner.login, page)
        }
    }
}