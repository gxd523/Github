package com.github.presenter

import com.github.model.issue.MyIssuePage
import com.github.network.entities.Issue
import com.github.common.CommonListPresenter
import com.github.ui.view.fragments.subfragments.MyIssueListFragment

/**
 * Created by benny on 7/9/17.
 */
class MyIssuePresenter : CommonListPresenter<Issue, MyIssueListFragment>() {
    override val listPage = MyIssuePage()
}