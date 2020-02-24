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
    private val baseCurrency: BehaviorSubject<String> =
        BehaviorSubject.createDefault(state.items.values.first().currency.code())
    private val baseAmount: BehaviorSubject<Double> =
        BehaviorSubject.createDefault(state.items.values.first().amount)

    private val subs = CompositeDisposable()

    init {
        loadRates()
    }

    fun getState() = stateSubject.toFlowable(BackpressureStrategy.LATEST)

    fun baseChanged(base: RateItem) {
        baseCurrency.onNext(base.currency.code())
        baseAmount.onNext(base.amount)
    }

    fun baseAmountChanged(newAmount: Double) {
        baseAmount.onNext(newAmount)
    }

    override fun onCleared() {
        subs.clear()
        super.onCleared()
    }

    private fun loadRates() {
        subs.add(
            baseCurrency.toFlowable(BackpressureStrategy.LATEST)
                .distinctUntilChanged()
                .switchMap(ratesRepository::latestRates)
                .switchMap { rates ->
                    baseAmount.toFlowable(BackpressureStrategy.LATEST)
                        .distinctUntilChanged()
                        .map { amount -> Pair(amount, rates) }
                }
                .subscribe(
                    { (baseAmount, rates) ->
                        val newItems = state.items.toMutableMap()
                        val newBase = newItems.getValue(rates.baseCurrencyCode)
                            .copy(
                                amount = baseAmount,
                                priority = System.currentTimeMillis()
                            )
                        newItems[rates.baseCurrencyCode] = newBase
                        rates.currencyCodeToRate.entries.forEach { (code, rate) ->
                            val newAmount = baseAmount * rate
                            val newItem = newItems[code]?.copy(amount = newAmount)
                                ?: RateItem(CurrencyImpl(code), newAmount)
                            newItems[code] = newItem
                        }
                        setState { copy(items = newItems, error = null) }
                    },
                    { setState { copy(error = it) } }
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