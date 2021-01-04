package com.github.model.page

import com.github.common.log.logger
import com.github.common.otherwise
import com.github.common.yes
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

abstract class ListPage<DataType> : DataProvider<DataType> {
    companion object {
        const val PAGE_SIZE = 20
    }

    private var currentPage = 1

    val data = GitHubPaging<DataType>()

    fun loadMore(): Observable<GitHubPaging<DataType>> = data.hasNext
        .yes {
            getData(currentPage + 1)
        }
        .otherwise {
            getData(currentPage + 1)
        }
        .doOnNext {
            currentPage += 1
        }
        .doOnError {
            logger.error("loadMore Error", it)
        }
        .map {
            data.mergeData(it)
            data
        }

    /**
     * 加载前pageCount页
     */
    fun loadFromFirst(pageCount: Int = currentPage): Observable<GitHubPaging<DataType>> =
        Observable.range(1, pageCount)
            .concatMap {
                getData(it)
            }
            .doOnError {
                logger.error("loadFromFirst, pageCount=$pageCount", it)
            }
            .reduce { acc, page ->
                acc.mergeData(page)
            }
            .doOnNext {
                data.clear()
                data.mergeData(it)
            }
}