package com.github.ui.view.fragments.subfragments

import com.bennyhuo.tieguanyin.annotations.Builder
import com.bennyhuo.tieguanyin.annotations.Optional
import com.bennyhuo.tieguanyin.annotations.Required
import com.github.network.entities.User
import com.github.presenter.PeopleListPresenter
import com.github.common.CommonListFragment

/**
 * Created by benny on 7/9/17.
 */
@Builder
class PeopleListFragment : CommonListFragment<User, PeopleListPresenter>() {
    @Optional
    lateinit var login: String

    @Required
    lateinit var type: String

    override val adapter = PeopleListAdapter()
}