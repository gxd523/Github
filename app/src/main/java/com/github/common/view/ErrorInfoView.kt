package com.github.common.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.setPadding
import com.github.layout.MATCH_PARENT
import com.github.layout.WRAP_CONTENT
import com.github.util.dp
import org.jetbrains.anko.*

/**
 * Created by benny on 7/15/17.
 */
class ErrorInfoView(val parentView: ViewGroup) : FrameLayout(parentView.context) {
    private var textView: TextView

    var isShowing = false

    init {
        setBackgroundColor(Color.WHITE)
        textView = TextView(context).apply {
            textSize = 18f
            setTextColor(Color.BLACK)
            setPadding(5f.dp.toInt())
            this@ErrorInfoView.addView(
                this,
                LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply { gravity = Gravity.CENTER }
            )
        }
    }

    fun show(text: String) {
        if (!isShowing) {
            parentView.addView(this, MATCH_PARENT, MATCH_PARENT)
            alpha = 0f
            animate().alpha(1f).setDuration(100).start()
            isShowing = true
        }
        textView.text = text
    }

    fun dismiss() {
        if (isShowing) {
            parentView.startViewTransition(this)
            parentView.removeView(this)
            animate().alpha(0f).setDuration(100).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    parentView.endViewTransition(this@ErrorInfoView)
                }
            }).start()
            isShowing = false
        }
    }

    fun show(@StringRes textRes: Int) {
        show(context.getString(textRes))
    }
}