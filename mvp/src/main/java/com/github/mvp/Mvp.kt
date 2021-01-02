package com.github.mvp

// TODO: 1/2/21 重点：泛型相互引用
interface IPresenter<out Viewer : IViewer<IPresenter<Viewer>>> : ILifecycle {
    val viewer: Viewer
}

interface IViewer<out Presenter : IPresenter<IViewer<Presenter>>> : ILifecycle {
    val presenter: Presenter
}
