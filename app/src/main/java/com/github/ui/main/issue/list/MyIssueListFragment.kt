package com.github.ui.main.issue.list

import com.github.common.fragment.CommonListFragment
import com.github.network.entities.Issue
import com.github.presenter.MyIssuePresenter

/**
 * Created by benny on 7/9/17.
 */
class MyIssueListFragment : CommonListFragment<Issue, MyIssuePresenter>() {
    companion object {
        const val REPOSITORY_NAME = "repository_name"
        const val OWNER_LOGIN = "owner_login"
    }

    override val adapter = IssueListAdapter()
}