package com.alexvit.revolutrates.rates.list

interface RateListener {
    fun onItemClicked(item: RateItem)
    fun onAmountChanged(item: RateItem, newAmount: Double)
}