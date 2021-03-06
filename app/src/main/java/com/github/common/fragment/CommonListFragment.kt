package com.github.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.R
import com.github.common.CommonListPresenter
import com.github.common.adapter.CommonListAdapter
import com.github.common.view.ErrorInfoView
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import com.github.model.page.ListPage
import com.github.mvp.impl.BaseFragment
import kotlinx.android.synthetic.main.common_list.*
import org.jetbrains.anko.toast
import retrofit2.adapter.rxjava.GitHubPaging

abstract class CommonListFragment<DataType, out Presenter : CommonListPresenter<DataType, CommonListFragment<DataType, Presenter>>> :
    BaseFragment<Presenter>() {
    protected abstract val adapter: CommonListAdapter<DataType>

    private val errorInfoView by lazy {
        ErrorInfoView(rootView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.common_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshView.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)

        recyclerView.adapter = LuRecyclerViewAdapter(adapter)
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()

        refreshView.isRefreshing = true

        recyclerView.setOnLoadMoreListener(presenter::loadMore)

        refreshView.setOnRefreshListener(presenter::refreshData)
        presenter.initData()
    }

    fun setLoadMoreEnable(isEnabled: Boolean) {
        recyclerView.setLoadMoreEnabled(isEnabled)
    }

    fun onDataInit(data: GitHubPaging<DataType>) {
        adapter.dataList.clear()
        adapter.dataList.addAll(data)
        recyclerView.setNoMore(data.isLast)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        refreshView.isRefreshing = false
        dismissError()
    }

    fun onDataRefresh(data: GitHubPaging<DataType>) {
        onDataInit(data)
    }

    fun onDataInitWithNothing() {
        showError("No Data.")
        recyclerView.setNoMore(true)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        refreshView.isRefreshing = false
        errorInfoView.isClickable = false
    }

    fun onDataInitWithError(error: String) {
        showError(error)
        errorInfoView.setOnClickListener {
            presenter.initData()
        }
    }

    fun onDataRefreshWithError(error: String) {
        if (adapter.dataList.isEmpty()) {
            showError(error)
            errorInfoView.setOnClickListener {
                presenter.initData()
            }
        } else {
            requireContext().toast(error)
        }
    }

    fun onMoreDataLoaded(data: GitHubPaging<DataType>) {
        adapter.dataList.update(data)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        recyclerView.setNoMore(data.isLast)
        dismissError()
    }

    fun onMoreDataLoadedWithError(error: String) {
        showError(error)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        errorInfoView.setOnClickListener {
            presenter.initData()
        }
    }

    protected open fun showError(error: String) {
        errorInfoView.show(error)
    }

    protected open fun dismissError() {
        errorInfoView.dismiss()
    }
}