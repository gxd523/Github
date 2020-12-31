package com.github.settings

import com.github.AppContext
import com.github.common.log.logger
import com.github.util.deviceId

object Configs {
    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "39e7c10c981b5662f7d8"
        const val clientSecret = "6858505801777dd721a1a1ba87c7cdeec1d87b22"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"
        const val callbackUrl = "http://localhost:9527/oauth/callback/github"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: " + it) }
        }
    }

}