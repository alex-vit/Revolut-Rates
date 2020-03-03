package com.alexvit.revolutrates.common

import io.reactivex.Observer

interface UiView {

    val eventConsumer: Observer<UiEvent>

    fun emit(event: UiEvent) {
        eventConsumer.onNext(event)
    }

}