package com.alexvit.revolutrates.common

import android.view.View
import io.reactivex.Observer

interface UiView {

    fun getView(): View

    fun getEventConsumer(): Observer<UiEvent>

    fun emit(event: UiEvent) {
        getEventConsumer().onNext(event)
    }

}