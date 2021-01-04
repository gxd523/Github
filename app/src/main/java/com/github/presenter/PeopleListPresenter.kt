package com.github.presenter

import com.github.model.page.ListPage
import com.github.model.people.PeoplePage
import com.github.model.people.PeoplePageParams
import com.github.network.entities.User
import com.github.common.CommonListPresenter
import com.github.ui.view.fragments.subfragments.PeopleListFragment

/**
 * Created by benny on 7/9/17.
 */
class PeopleListPresenter : CommonListPresenter<User, PeopleListFragment>() {

    override val listPage: ListPage<User> by lazy {
        PeoplePage(PeoplePageParams(viewer.type, viewer.login))
    }

}