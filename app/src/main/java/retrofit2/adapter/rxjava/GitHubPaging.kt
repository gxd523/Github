package retrofit2.adapter.rxjava

import com.github.common.log.logger
import okhttp3.HttpUrl

class GitHubPaging<T> : ArrayList<T>() {
    private val relMap = HashMap<String, String?>().withDefault { null }

    // TODO: 1/3/21 重点：HashMap代理属性，MapAccessors内有Map.getValue()，返回值为map以property作为key的返回值
    private val first by relMap
    private val last by relMap
    private val next by relMap
    private val prev by relMap

    val isLast
        get() = last == null

    val hasNext
        get() = next != null

    val isFirst
        get() = first == null

    val hasPrev
        get() = prev != null

    var since: Int = 0

    operator fun get(key: String): String? {
        return relMap[key]
    }

    fun setupLinks(link: String) {
        Regex("""<($URL_PATTERN)>; rel="(\w+)"""").findAll(link).asIterable()// TODO: 1/3/21 重点：正则表达式
            .map { matchResult ->
                val url = matchResult.groupValues[1]
                relMap[matchResult.groupValues[3]] = url // next=....
                if (url.contains("since")) {
                    HttpUrl.parse(url)?.queryParameter("since")?.let {
                        since = it.toInt()
                    }
                }
                logger.warn("${matchResult.groupValues[3]} => ${matchResult.groupValues[1]}")
            }
    }

    fun mergeData(paging: GitHubPaging<T>): GitHubPaging<T> {
        addAll(paging)
        relMap.clear()
        relMap.putAll(paging.relMap)
        since = paging.since
        return this
    }

    companion object {
        const val URL_PATTERN = """(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"""
    }
}