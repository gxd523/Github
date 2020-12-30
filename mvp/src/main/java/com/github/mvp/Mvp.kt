package com.github.mvp

interface IPresenter<out Viewer : IViewer<IPresenter<Viewer>>> : ILifecycle {
    val viewer: Viewer
}

interface IViewer<out Presenter : IPresenter<IViewer<Presenter>>> : ILifecycle {
    val presenter: Presenter
}
