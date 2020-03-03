package com.alexvit.revolutrates.common

import androidx.annotation.CallSuper
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.Disposable

interface UiPresenter<View : UiView, ParentState : Any, State : Any> {

    val view: View
    val parentState: Flowable<ParentState>
    var subscription: Disposable

    @CallSuper
    fun start() {
        subscription.dispose()
        subscription = parentState
            .compose(getStateTransformer())
            .subscribe(::onState)
    }

    @CallSuper
    fun stop() {
        subscription.dispose()
    }

    fun onState(state: State)

    fun getStateTransformer(): FlowableTransformer<ParentState, State>

}