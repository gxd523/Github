package com.github

import com.github.common.SpExt

object Settings {
    var email: String by SpExt(AppContext, "")
    var password: String by SpExt(AppContext, "")
}