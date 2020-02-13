package com.alexvit.revolutrates.currency

interface Currency {

    companion object {
        @Throws(IllegalArgumentException::class)
        fun from(currencyIsoCode: String): Currency =
            CurrencyImpl(currencyIsoCode)
    }

    fun name(): String

    fun code(): String

    fun format(amount: Double): String

}
