package com.alexvit.revolutrates.rates

import com.alexvit.revolutrates.currency.Currency
import com.alexvit.revolutrates.rates.list.RateItem

data class RatesState(
    val items: Map<String, RateItem> = mapOf(
        Currency.DEFAULT_CURRENCY_CODE to RateItem(Currency.default(), 1.0, 1)
    ),
    val error: Throwable? = null
)
