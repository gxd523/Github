package com.github.network.entities

import com.github.common.anno.PoKo
import com.github.settings.Configs

@PoKo
data class DeviceAndUserCodeResponse(
    var device_code: String,
    var user_code: String,
    var verification_uri: String,
    var expires_in: Int,
    var interval: Int,
)

@PoKo
data class AccessTokenRequest(
    var client_id: String = Configs.Account.clientId,
    var device_code: String,
    var grant_type: String = "urn:ietf:params:oauth:grant-type:device_code",
)

@PoKo
data class DeviceAndUserCodeRequest(
    var client_id: String = Configs.Account.clientId,
    var scope: String = Configs.Account.scope,
)