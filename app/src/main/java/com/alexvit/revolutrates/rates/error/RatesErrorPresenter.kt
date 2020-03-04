package com.alexvit.revolutrates.rates.error

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import com.alexvit.revolutrates.common.LifecycleUiPresenter
import com.alexvit.revolutrates.rates.RatesState
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

internal class RatesErrorPresenter(
    view: RatesErrorView,
    parentState: Flowable<RatesState>,
    lifecycle: Lifecycle
) : LifecycleUiPresenter<RatesErrorView, RatesState, RatesErrorState>(
    view,
    parentState,
    lifecycle
) {

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun formatError(error: Throwable): String =
            listOfNotNull(error::class.java.simpleName, error.message)
                .joinToString(":\n")
    }

    override fun onState(state: RatesErrorState) {
        when (state.errorMessage) {
            null -> view.hide()
            else -> {
                view.show()
                view.setErrorMessage(state.errorMessage)
            }
        }
    }

    override fun getStateTransformer(): FlowableTransformer<RatesState, RatesErrorState> =
        FlowableTransformer { upstream ->
            upstream
                .map {
                    RatesErrorState(
                        it.error?.let(
                            Companion::formatError
                        )
                    )
                }
                .distinctUntilChanged()
        }

}