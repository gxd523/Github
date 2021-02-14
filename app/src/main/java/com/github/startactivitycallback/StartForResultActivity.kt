package com.github.startactivitycallback

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


fun <T : Activity> startActivityCallback(
    activity: AppCompatActivity,
    activityCls: Class<T>,
    requestCode: Int = 123,
    startActivityCallback: () -> Unit,
) {
    var fragment = activity.supportFragmentManager.findFragmentByTag(StartActivityForResultFragment.TAG)
    if (fragment !is StartActivityForResultFragment) {
        fragment = StartActivityForResultFragment()
        activity.supportFragmentManager
            .beginTransaction()
            .add(fragment, StartActivityForResultFragment.TAG)
            .commitNow()
    }
    fragment.startActivityCallback = startActivityCallback
    startActivityCallbackList += startActivityCallback
    val intent = Intent(activity, activityCls)
    fragment.startActivityForResult(intent, requestCode)
}