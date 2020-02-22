package com.alexvit.revolutrates

import androidx.lifecycle.ViewModel
import com.alexvit.revolutrates.currency.CurrencyImpl
import com.alexvit.revolutrates.ratelist.ExchangeRateItem
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.subjects.BehaviorSubject

class MainViewModel : ViewModel() {

    private val items: MutableMap<String, ExchangeRateItem> = listOf(
        "sgd",
        "thb",
        "zar",
        "sek",
        "pln",
        "ron",
        "rub",
        "nok",
        "nzd",
        "PHP",
        "krw",
        "MXN",
        "MYR",
        "ils",
        "inr",
        "isk",
        "jpy",
        "HUF",
        "idr",
        "GBP",
        "HKD",
        "HRK",
        "EUR",
        "USD",
        "AUD",
        "bgn",
        "brl",
        "cad",
        "chf",
        "cny",
        "CZK",
        "DKK",
        "OHAI"
    )
        .map { code ->
            code to ExchangeRateItem(
                CurrencyImpl.from(code),
                5.0
            )
        }
        .toMap()
        .toMutableMap()

    private val itemSubject: BehaviorSubject<List<ExchangeRateItem>> =
        BehaviorSubject.createDefault(items.values.toList())

    internal fun getItems() = itemSubject.toFlowable(LATEST)

    internal fun onItemClicked(item: ExchangeRateItem) {
        val newList = listOf(item) + items.values.filter { it != item }
        itemSubject.onNext(newList)
    }
}