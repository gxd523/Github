package com.github.common

import com.github.model.page.ListPage
import com.github.mvp.impl.BasePresenter
import rx.Subscription

abstract class CommonListPresenter<DataType, out Viewer : CommonListFragment<DataType, CommonListPresenter<DataType, Viewer>>> :
    BasePresenter<Viewer>() {
    abstract val listPage: ListPage<DataType>
    private var firstInView = true

    fun initData() {
        listPage.loadFromFirst()
            .subscribe({
                if (it.isEmpty()) viewer.onDataInitWithNothing() else viewer.onDataInit(it)
            }, {
                viewer.onDataInitWithError(it.message ?: it.toString())
            }).let(subscriptionList::add)
    }

    fun refreshData() {
        listPage.loadFromFirst()
            .subscribe(
                { if (it.isEmpty()) viewer.onDataInitWithNothing() else viewer.onDataRefresh(it) },
                { viewer.onDataRefreshWithError(it.message ?: it.toString()) }
            ).let(subscriptionList::add)
    }

    fun loadMore() {
        listPage.loadMore()
            .subscribe(
                { viewer.onMoreDataLoaded(it) },
                { viewer.onMoreDataLoadedWithError(it.message ?: it.toString()) }
            ).let(subscriptionList::add)
    }

    override fun onResume() {
        super.onResume()
        if (!firstInView) {
            refreshData()
        }
        firstInView = false
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionList.forEach(Subscription::unsubscribe)
        subscriptionList.clear()
    }
}