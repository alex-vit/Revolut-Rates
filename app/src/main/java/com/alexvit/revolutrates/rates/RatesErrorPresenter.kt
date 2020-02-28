package com.alexvit.revolutrates.rates

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import com.alexvit.revolutrates.common.UiPresenter
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import timber.log.Timber

internal class RatesErrorPresenter(
    view: RatesErrorView,
    parentState: Flowable<RatesState>,
    lifecycle: Lifecycle
) : UiPresenter<RatesState, RatesErrorState, RatesErrorView>(
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

    override fun onState(newState: RatesErrorState) {
        Timber.d("WTF $newState")
        when (newState.errorMessage) {
            null -> view.hide()
            else -> {
                view.show()
                view.setErrorMessage(newState.errorMessage)
            }
        }
    }

    override fun stateTransformer(): FlowableTransformer<RatesState, RatesErrorState> =
        FlowableTransformer { upstream ->
            upstream
                .map {
                    RatesErrorState(
                        it.error?.let(
                            ::formatError
                        )
                    )
                }
                .distinctUntilChanged()
        }

}