package com.github.presenter

import com.github.common.CommonListPresenter
import com.github.model.page.ListPage
import com.github.model.people.PeoplePage
import com.github.model.people.PeoplePageParams
import com.github.network.entities.User
import com.github.ui.main.people.list.PeopleListFragment

class PeopleListPresenter : CommonListPresenter<User, PeopleListFragment>() {

    override val listPage: ListPage<User> by lazy {
        PeoplePage(PeoplePageParams(viewer.type, viewer.login))
    }

}