package com.alexvit.revolutrates.ratelist

interface RateListener {
    fun onItemClicked(item: RateItem)
    fun onAmountChanged(item: RateItem, newAmount: Double)
}