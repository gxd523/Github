package com.github.network.entities

import com.github.common.anno.PoKo
import retrofit2.adapter.rxjava.PagingWrapper

@PoKo
data class SearchRepositoryList(
    var total_count: Int,
    var incomplete_results: Boolean,
    var items: List<Repository>,
) : PagingWrapper<Repository>() {
    override fun getElements() = items
}