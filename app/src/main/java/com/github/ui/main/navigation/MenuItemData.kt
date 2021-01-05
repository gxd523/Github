package com.github.ui.main.navigation

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.github.R
import com.github.ui.main.about.AboutFragment
import com.github.ui.main.issue.MyIssueFragment
import com.github.ui.main.people.PeopleFragment
import com.github.ui.main.repo.RepoFragment

data class MenuItemData(
    private val groupId: Int = 0,
    val title: String,
    @DrawableRes val icon: Int,
    val fragmentClass: Class<out Fragment>,
    val arguements: Bundle? = null,
) {
    companion object {
        private val menuItemMap = mapOf(
            R.id.navRepos to MenuItemData(0, "Repository", R.drawable.ic_repository, RepoFragment::class.java),
            R.id.navPeople to MenuItemData(0, "People", R.drawable.ic_people, PeopleFragment::class.java),
            R.id.navIssue to MenuItemData(0, "Issue", R.drawable.ic_issue, MyIssueFragment::class.java),
            R.id.navAbout to MenuItemData(0, "About", R.drawable.ic_about_us, AboutFragment::class.java)
        )

        operator fun get(@IdRes navId: Int): MenuItemData {
            return menuItemMap[navId] ?: (menuItemMap[R.id.navRepos] ?: error(""))
        }

        operator fun get(itemData: MenuItemData): Int {
            return menuItemMap.filter { it.value == itemData }.keys.first()
        }
    }

    override fun toString(): String {
        return "NavViewItem(groupId=$groupId, title='$title', icon=$icon, fragmentClass=$fragmentClass, arguements=$arguements)"
    }
}
