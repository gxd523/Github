package com.github.settings

import com.github.AppContext
import com.github.R
import com.github.model.account.AccountManager
import com.github.util.sp

object Settings {
    var lastPage: Int
        get() = if (lastPageIdString.isEmpty()) 0 else AppContext.resources.getIdentifier(lastPageIdString,
            "id",
            AppContext.packageName)
        set(value) {
            lastPageIdString = AppContext.resources.getResourceEntryName(value)
        }

    val defaultPage
        get() = if (AccountManager.isLoggedIn()) defaultPageForUser else defaultPageForVisitor

    private var defaultPageForUser by sp(R.id.navRepos)

    private var defaultPageForVisitor by sp(R.id.navRepos)

    private var lastPageIdString by sp("")
}