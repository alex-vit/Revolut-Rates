package com.alexvit.revolutrates.common

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

abstract class LifecycleUiPresenter<View : UiView, ParentState : Any, State : Any>(
    override val view: View,
    override val parentState: Flowable<ParentState>,
    lifecycle: Lifecycle
) :
    UiPresenter<View, ParentState, State>, LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    override var subscription: Disposable = Disposables.disposed()

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun start() {
        super.start()
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun stop() {
        super.stop()
    }

}