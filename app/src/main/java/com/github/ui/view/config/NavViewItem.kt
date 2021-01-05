package com.github.ui.view.config

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

class NavViewItem private constructor(
    private val groupId: Int = 0,
    val title: String,
    @DrawableRes val icon: Int,
    val fragmentClass: Class<out Fragment>,
    val arguements: Bundle = Bundle(),
) {

    companion object {
        private val items = mapOf(
            R.id.navRepos to NavViewItem(
                0,
                "Repository",
                R.drawable.ic_repository,
                RepoFragment::class.java,
                Bundle().apply { putParcelable(RepoListFragment.OPTIONAL_user, null) }
            ),
            R.id.navPeople to NavViewItem(0, "People", R.drawable.ic_people, PeopleFragment::class.java),
            R.id.navIssue to NavViewItem(0, "Issue", R.drawable.ic_issue, MyIssueFragment::class.java),
            R.id.navAbout to NavViewItem(0, "About", R.drawable.ic_about_us, AboutFragment::class.java)
        )

        operator fun get(@IdRes navId: Int): NavViewItem {
            return items[navId] ?: (items[R.id.navRepos] ?: error(""))
        }

        operator fun get(item: NavViewItem): Int {
            return items.filter { it.value == item }.keys.first()
        }
    }

    override fun toString(): String {
        return "NavViewItem(groupId=$groupId, title='$title', icon=$icon, fragmentClass=$fragmentClass, arguements=$arguements)"
    }
}