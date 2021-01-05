package com.github.settings

import com.github.AppContext
import com.github.R
import com.github.model.account.AccountManager
import com.github.util.sp

object Settings {
    private var selectMenuItemIdName by sp("")

    var selectMenuItemId: Int
        get() = if (selectMenuItemIdName.isEmpty()) 0 else
            AppContext.resources.getIdentifier(selectMenuItemIdName, "id", AppContext.packageName)
        set(value) {
            selectMenuItemIdName = AppContext.resources.getResourceEntryName(value)
        }

    val defaultMenuItemId
        get() = if (AccountManager.isLoggedIn()) R.id.navRepos else R.id.navRepos
}