package com.github.ui.view.common

import com.github.model.page.ListPage
import com.github.mvp.impl.BasePresenter
import rx.Subscription

abstract class CommonListPresenter<DataType, out View : CommonListFragment<DataType, CommonListPresenter<DataType, View>>> :
    BasePresenter<View>() {
    abstract val listPage: ListPage<DataType>

    private var firstInView = true
    private val subcriptionList = ArrayList<Subscription>()

    fun initData() {
        listPage.loadFromFirst()
            .subscribe({
                if (it.isEmpty()) viewer.onDataInitWithNothing() else viewer.onDataInit(it)
            }, {
                viewer.onDataInitWithError(it.message ?: it.toString())
            }).let(subcriptionList::add)
    }

    fun refreshData() {
        listPage.loadFromFirst()
            .subscribe(
                { if (it.isEmpty()) viewer.onDataInitWithNothing() else viewer.onDataRefresh(it) },
                { viewer.onDataRefreshWithError(it.message ?: it.toString()) }
            ).let(subcriptionList::add)
    }

    fun loadMore() {
        listPage.loadMore()
            .subscribe(
                { viewer.onMoreDataLoaded(it) },
                { viewer.onMoreDataLoadedWithError(it.message ?: it.toString()) }
            ).let(subcriptionList::add)
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
        subcriptionList.forEach(Subscription::unsubscribe)
        subcriptionList.clear()
    }
}