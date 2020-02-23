package com.alexvit.revolutrates.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexvit.revolutrates.currency.CurrencyImpl
import com.alexvit.revolutrates.rates.data.RatesRepository
import com.alexvit.revolutrates.rates.list.RateItem
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class RatesViewModel(private val ratesRepository: RatesRepository) : ViewModel() {

    private var state = RatesState()
    private val stateSubject: BehaviorSubject<RatesState> = BehaviorSubject.createDefault(state)

    private val subs = CompositeDisposable()

    init {
        loadRates()
    }

    fun itemClicked(item: RateItem) {
        subs.clear()
        val topCode = item.currency.code()
        val newTopCodes = listOf(topCode) + state.topCurrencyCodes.filterNot { it == topCode }
        setState {
            copy(
                topCurrencyCodes = newTopCodes,
                baseAmount = item.amount
//                ,
//                rates = mapOf()
            )
        }
        loadRates()
    }

    fun amountChanged(item: RateItem, newAmount: Double) {
        if (state.topCurrencyCodes.first() == item.currency.code()) {
            setState { copy(baseAmount = newAmount) }
        }
    }

    fun getState() = stateSubject.toFlowable(BackpressureStrategy.LATEST)

    fun getItems() = stateSubject.toFlowable(BackpressureStrategy.LATEST)
        .map { state ->
            val items: MutableList<RateItem> = mutableListOf()
            val baseCode = state.topCurrencyCodes.first()
            val baseAmount = state.baseAmount
            val baseItem = RateItem(
                CurrencyImpl(baseCode), baseAmount
            )
            items.add(baseItem)
            val topRates = state.topCurrencyCodes.drop(1)
                .map { code -> code to (state.rates[code] ?: 1.0) }
            items.addAll(topRates.map { (code, rate) ->
                RateItem(
                    CurrencyImpl(code),
                    rate * baseAmount
                )
            })
            val rest = state.rates.filterKeys { code -> code !in state.topCurrencyCodes }
                .map { (code, rate) ->
                    RateItem(
                        CurrencyImpl(code),
                        rate * baseAmount
                    )
                }
            items.addAll(rest)
            items
        }

    private fun loadRates() {
        subs.add(
            ratesRepository.latestRates(state.topCurrencyCodes.first()).subscribe(
                { rates -> setState { copy(rates = rates) } },
                { error ->
                    setState {
                        RatesState(
                            error = error
                        )
                    }
                }
            )
        )
    }

    private fun setState(update: RatesState.() -> RatesState) {
        state = state.update()
        stateSubject.onNext(state)
    }

    class Factory(private val ratesRepository: RatesRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            RatesViewModel(ratesRepository) as T
    }
}