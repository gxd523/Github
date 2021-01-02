package com.github.settings

import com.github.AppContext
import com.github.common.log.logger
import com.github.util.deviceId

object Configs {
    object Account {
        const val scope = "user,repo,notifications,gist,admin:org"
        const val clientId = "c9763c28bc8e6625bbc1"

        const val clientSecret = "454f873e4651f504138ef3fbeb00f6847e361e31"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"
        const val callbackUrl = "http://localhost:9527/oauth/callback/github"
        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: $it") }
        }
    }

}