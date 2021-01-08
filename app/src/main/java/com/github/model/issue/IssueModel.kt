package com.github.model.issue

import com.github.model.page.ListPage
import com.github.network.entities.Issue
import com.github.network.services.IssueService
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

class MyIssuePage : ListPage<Issue>() {
    override fun getData(page: Int): Observable<GitHubPaging<Issue>> {
        return IssueService.listIssuesOfAuthenticatedUser(page = page)
    }
}