package com.github.ui.detail

import android.app.Activity
import android.view.GestureDetector
import android.view.MotionEvent
import com.bennyhuo.swipefinishable.SwipeFinishable
import com.github.R
import com.github.common.CommonDetailActivity
import com.github.util.dp

abstract class BaseDetailSwipeFinishActivity : CommonDetailActivity(), SwipeFinishable.SwipeFinishableActivity {
    override fun finishThisActivity() {
        super.finish()
    }

    override fun finish() {
        SwipeFinishable.INSTANCE.finishCurrentActivity()
    }
}

abstract class BaseDetailActivity : CommonDetailActivity() {
    private val swipeBackTouchDelegate by lazy { SwipeBackTouchDelegate(this, ::finish) }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return swipeBackTouchDelegate.onTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in, R.anim.rignt_out)
    }
}

class SwipeBackTouchDelegate(val activity: Activity, block: () -> Unit) {
    companion object {
        private const val MIN_FLING_TO_BACK_DP = 450f
    }

    private val swipeBackDelegate by lazy {
        GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                return if (velocityX > MIN_FLING_TO_BACK_DP.dp) {
                    block()
                    true
                } else {
                    false
                }
            }
        })
    }

    fun onTouchEvent(event: MotionEvent) = swipeBackDelegate.onTouchEvent(event)
}