package com.github

import com.github.common.SpExt

object Settings {
    var username: String by SpExt(AppContext, "")
    var password: String by SpExt(AppContext, "")
}