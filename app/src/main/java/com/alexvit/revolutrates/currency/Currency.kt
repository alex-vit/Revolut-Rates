package com.alexvit.revolutrates.currency

interface Currency {
    companion object {
        const val DEFAULT_CURRENCY_CODE = "EUR"
        fun default(): Currency = CurrencyImpl(DEFAULT_CURRENCY_CODE)
        fun allExceptDefault(): List<Currency> = listOf(
            "AUD",
            "BGN",
            "BRL",
            "CAD",
            "CHF",
            "CNY",
            "CZK",
            "DKK",
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
        ).map(::CurrencyImpl)
    }

    fun name(): String

    fun code(): String

    fun format(amount: Double): String

}
