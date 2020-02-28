package com.alexvit.revolutrates.common

import io.reactivex.Observer

abstract class UiView(
    private val eventConsumer: Observer<UiEvent>
) {

    protected fun emit(event: UiEvent) {
        eventConsumer.onNext(event)
    }

}