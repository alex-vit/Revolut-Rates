package com.alexvit.revolutrates.currency

interface Currency {

    companion object {
        const val DEFAULT_CURRENCY_CODE = "EUR"
        val SUPPORTED_CURRENCY_CODES = setOf(
            "AUD",
            "BGN",
            "BRL",
            "CAD",
            "CHF",
            "CNY",
            "CZK",
            "DKK",
            "EUR",
            "GBP",
            "HKD",
            "HRK",
            "HUF",
            "IDR",
            "ILS",
            "INR",
            "ISK",
            "JPY",
            "KRW",
            "MXN",
            "MYR",
            "NOK",
            "NZD",
            "PHP",
            "PLN",
            "RON",
            "RUB",
            "SEK",
            "SGD",
            "THB",
            "USD",
            "ZAR"
        )

        fun default(): Currency = CurrencyImpl(DEFAULT_CURRENCY_CODE)
    }

    fun name(): String

    fun code(): String

    fun format(amount: Double): String

}
