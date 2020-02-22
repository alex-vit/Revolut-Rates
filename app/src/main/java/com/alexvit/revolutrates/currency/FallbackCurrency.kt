package com.alexvit.revolutrates.currency

data class FallbackCurrency(private val currencyCode: String) : Currency {

    override fun name(): String = currencyCode

    override fun code(): String = currencyCode

    override fun format(amount: Double): String = "%.2f".format(amount)

}