package com.alexvit.revolutrates.rates.list

import com.alexvit.revolutrates.currency.Currency

data class RateItem(val currency: Currency, val amount: Double, val priority: Long = 0)
