package com.github.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.github.R
import com.github.common.delegate.delegateOf
import com.github.common.delegate.delegateOf1
import com.github.util.dp
import com.github.util.subscribeIgnoreError
import kotlinx.android.synthetic.main.detail_item.view.*
import rx.Observable

class DetailItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CardView(context, attrs, defStyleAttr) {
    init {// TODO: 1/6/21 重点：注意类初始化顺序
        View.inflate(context, R.layout.detail_item, this)
    }

    var title: CharSequence by delegateOf(titleView::getText, titleView::setText)

    var content: CharSequence by delegateOf(contentView::getText, contentView::setText, "")

    var icon by delegateOf1(setter = iconView::setImageResource, defaultValue = 0)

    var operatorIcon by delegateOf1(setter = operatorIconView::setBackgroundResource, defaultValue = 0)

    var isChecked by delegateOf(operatorIconView::isChecked, operatorIconView::setChecked)

    var checkEvent: ((Boolean) -> Observable<Boolean>)? = null

    init {
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
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        (layoutParams as MarginLayoutParams).leftMargin = 15f.dp.toInt()
        (layoutParams as MarginLayoutParams).rightMargin = 15f.dp.toInt()
        (layoutParams as MarginLayoutParams).topMargin = 1f.dp.toInt()
    }
}