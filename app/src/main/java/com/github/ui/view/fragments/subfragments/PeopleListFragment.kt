package com.github.ui.view.fragments.subfragments

import android.os.Bundle
import com.github.common.CommonListFragment
import com.github.model.people.PeoplePage
import com.github.network.entities.User
import com.github.presenter.PeopleListPresenter

class PeopleListFragment : CommonListFragment<User, PeopleListPresenter>() {
    companion object {
        const val REQUIRED_type = "type"
        const val OPTIONAL_login = "login"
    }

    var login: String? = null

    lateinit var type: String

    override val adapter = PeopleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = arguments?.getString(OPTIONAL_login)
        type = arguments?.getString(REQUIRED_type) ?: PeoplePage.Type.ALL.name
    }
}