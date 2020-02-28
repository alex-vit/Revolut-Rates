package com.alexvit.revolutrates.common

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.CompositeDisposable

abstract class UiPresenter<ParentState : Any, State : Any, View : UiView>(
    private val view: View,
    private val parentState: Flowable<ParentState>,
    lifecycle: Lifecycle? = null
) : LifecycleObserver {

    private val subs = CompositeDisposable()
    private var started = false

    init {
        lifecycle?.addObserver(this)
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun start() {
        if (!started) {
            subs.add(parentState.compose(stateTransformer()).subscribe(::onState))
            started = true
        } else {
            throw IllegalStateException("start called when already started")
        }
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun stop() {
        subs.clear()
        started = false
    }

    protected abstract fun onState(newState: State)

    protected abstract fun stateTransformer(): FlowableTransformer<ParentState, State>

}