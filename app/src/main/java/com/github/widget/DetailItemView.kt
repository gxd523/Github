package com.github.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.TintableToggleButton
import androidx.cardview.widget.CardView
import com.github.R
import com.github.common.delegate.delegateLazyOf
import com.github.util.dp
import com.github.util.subscribeIgnoreError
import kotlinx.android.synthetic.main.detail_item.view.*
import rx.Observable

class DetailItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CardView(context, attrs, defStyleAttr) {
    var title: CharSequence by delegateLazyOf(TextView::getText, TextView::setText) {
        titleView
    }

    var content: CharSequence by delegateLazyOf(TextView::getText, TextView::setText, "") {
        contentView
    }

    var icon by delegateLazyOf(setter = ImageView::setImageResource, defaultValue = 0) {
        iconView
    }

    var operatorIcon by delegateLazyOf(setter = TintableToggleButton::setBackgroundResource, defaultValue = 0) {
        operatorIconView
    }

    var isChecked by delegateLazyOf(TintableToggleButton::isChecked, TintableToggleButton::setChecked) {
        operatorIconView
    }

    var checkEvent: ((Boolean) -> Observable<Boolean>)? = null

    init {
        View.inflate(context, R.layout.detail_item, this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DetailItemView)
            title = typedArray.getString(R.styleable.DetailItemView_item_title) ?: ""
            content = typedArray.getString(R.styleable.DetailItemView_item_content) ?: ""
            icon = typedArray.getResourceId(R.styleable.DetailItemView_item_icon, 0)
            operatorIcon = typedArray.getResourceId(R.styleable.DetailItemView_item_op_icon, 0)
            typedArray.recycle()
        }

        setOnClickListener {
            checkEvent?.invoke(isChecked)
                ?.subscribeIgnoreError {
                    isChecked = it
                }
        }

        post {
            (layoutParams as MarginLayoutParams).leftMargin = 15f.dp.toInt()
            (layoutParams as MarginLayoutParams).rightMargin = 15f.dp.toInt()
            (layoutParams as MarginLayoutParams).topMargin = 1f.dp.toInt()
        }
    }
}