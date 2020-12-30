package com.github.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import com.github.mvp.IPresenter
import com.github.mvp.IViewer

abstract class BasePresenter<out Viewer : IViewer<BasePresenter<Viewer>>> : IPresenter<Viewer> {
    override lateinit var viewer: @UnsafeVariance Viewer

    override fun onCreate(savedInstanceState: Bundle?) = Unit

    override fun onSaveInstanceState(outState: Bundle) = Unit

    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit

    override fun onConfigurationChanged(newConfig: Configuration) = Unit

    override fun onDestroy() = Unit

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun onResume() = Unit

    override fun onPause() = Unit
}