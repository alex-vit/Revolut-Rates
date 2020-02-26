package com.alexvit.revolutrates.rates

import androidx.annotation.VisibleForTesting
import com.alexvit.revolutrates.currency.Currency
import com.alexvit.revolutrates.rates.list.RateItem


data class RatesState(
    val items: Map<String, RateItem> = mapOf(
        Currency.DEFAULT_CURRENCY_CODE to RateItem(Currency.default(), DEFAULT_AMOUNT, 1)
    ),
    val error: Throwable? = null
) {
    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val DEFAULT_AMOUNT = 1.0
    }
}
