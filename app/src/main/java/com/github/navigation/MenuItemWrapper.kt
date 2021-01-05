package com.github.navigation

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.github.R
import com.github.ui.view.fragments.AboutFragment
import com.github.ui.view.fragments.MyIssueFragment
import com.github.ui.view.fragments.PeopleFragment
import com.github.ui.view.fragments.RepoFragment
import com.github.ui.view.fragments.subfragments.RepoListFragment

class MenuItemWrapper private constructor(
    private val groupId: Int = 0,
    val title: String,
    @DrawableRes val icon: Int,
    val fragmentClass: Class<out Fragment>,
    val arguements: Bundle = Bundle(),
) {

    companion object {
        private val menuItemMap = mapOf(
            R.id.navRepos to MenuItemWrapper(
                0,
                "Repository",
                R.drawable.ic_repository,
                RepoFragment::class.java,
                Bundle().apply { putParcelable(RepoListFragment.OPTIONAL_user, null) }
            ),
            R.id.navPeople to MenuItemWrapper(0, "People", R.drawable.ic_people, PeopleFragment::class.java),
            R.id.navIssue to MenuItemWrapper(0, "Issue", R.drawable.ic_issue, MyIssueFragment::class.java),
            R.id.navAbout to MenuItemWrapper(0, "About", R.drawable.ic_about_us, AboutFragment::class.java)
        )

        operator fun get(@IdRes navId: Int): MenuItemWrapper {
            return menuItemMap[navId] ?: (menuItemMap[R.id.navRepos] ?: error(""))
        }

        operator fun get(itemWrapper: MenuItemWrapper): Int {
            return menuItemMap.filter { it.value == itemWrapper }.keys.first()
        }
    }

    override fun toString(): String {
        return "NavViewItem(groupId=$groupId, title='$title', icon=$icon, fragmentClass=$fragmentClass, arguements=$arguements)"
    }
}
