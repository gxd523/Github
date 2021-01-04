package com.github.common

import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.util.dp

class CommonViewHolder(view: View, onItemClick: (View, Int) -> Unit) : RecyclerView.ViewHolder(view) {
    init {
        itemView.setOnTouchListener { _, event ->
            val (scale, transZ) = when (event.action) {
                MotionEvent.ACTION_DOWN -> Pair(1.03f, 10f.dp)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> Pair(1f, 0f)
                else -> Pair(1f, 0f)
            }
            ViewCompat // TODO: 1/4/21 重点：ViewCompat动画、z轴动画
                .animate(itemView)
                .scaleX(scale)
                .scaleY(scale)
                .translationZ(transZ)
                .duration = 100L
            false
        }

        itemView.setOnClickListener {
            onItemClick(itemView, adapterPosition)
        }
    }
}