package com.github.common

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.common.log.logger
import com.github.util.AdapterList

abstract class CommonListAdapter<T>(@LayoutRes val itemResId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)// 让item有自己的id
    }

    val dataList = AdapterList<T>(this)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: ViewGroup = inflater.inflate(R.layout.item_card, parent, false) as ViewGroup
        inflater.inflate(itemResId, itemView)
        return CommonViewHolder(itemView) { view, position ->
            onItemClicked(view, dataList[position])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private var oldPosition = -1

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is CommonViewHolder && holder.layoutPosition > oldPosition) {// TODO: 1/4/21 重点：列表动画，牛逼
            logger.debug("${holder.layoutPosition} > $oldPosition")
            ObjectAnimator
                .ofFloat(holder.itemView, View.TRANSLATION_Y, 500f, 0f)
                .setDuration(500)
                .start()
            oldPosition = holder.layoutPosition
        }
    }

    abstract fun onBindData(viewHolder: RecyclerView.ViewHolder, item: T)

    abstract fun onItemClicked(itemView: View, item: T)
}