package com.github.network.dns

import okhttp3.Dns
import java.net.InetAddress

val dnsMap = mapOf(
    "github.com" to "140.82.112.4",
    "gist.github.com" to "140.82.112.3",
    "camo.githubusercontent.com" to "199.232.96.133",
    "raw.githubusercontent.com" to "199.232.96.133",
    "gist.githubusercontent.com" to "199.232.96.133",
    "cloud.githubusercontent.com" to "199.232.96.133",
    "avatars0.githubusercontent.com" to "199.232.96.133",
    "avatars1.githubusercontent.com" to "199.232.96.133",
    "avatars2.githubusercontent.com" to "199.232.96.133",
    "avatars3.githubusercontent.com" to "199.232.96.133",
    "avatars4.githubusercontent.com" to "199.232.96.133",
)

fun customLookup(hostname: String): List<InetAddress> {
    return if (dnsMap.containsKey(hostname)) {
        mutableListOf(InetAddress.getByName(dnsMap[hostname]))
    } else {
        Dns.SYSTEM.lookup(hostname)
    }
}