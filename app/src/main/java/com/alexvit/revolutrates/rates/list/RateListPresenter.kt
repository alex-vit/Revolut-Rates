package com.alexvit.revolutrates.rates.list

import androidx.lifecycle.Lifecycle
import com.alexvit.revolutrates.common.LifecycleUiPresenter
import com.alexvit.revolutrates.common.Option
import com.alexvit.revolutrates.rates.RatesState
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

class RateListPresenter(
    override val view: RateListView,
    override val parentState: Flowable<RatesState>,
    lifecycle: Lifecycle
) : LifecycleUiPresenter<RateListView, RatesState, Option<Map<String, RateItem>>>(
    view,
    parentState,
    lifecycle
) {

    override var subscription: Disposable = Disposables.disposed()

    override fun onState(state: Option<Map<String, RateItem>>) {
        when (state) {
            is Option.Some -> {
                view.show()
                view.setItems(state.value)
            }
            is Option.None -> view.hide()
        }
    }

    override fun getStateTransformer(): FlowableTransformer<RatesState, Option<Map<String, RateItem>>> {
        return FlowableTransformer { upstream ->
            upstream.map {
                when {
                    it.error == null && it.items.isNotEmpty() -> Option.of(it.items)
                    else -> Option.none()
                }
            }
        }
    }
}