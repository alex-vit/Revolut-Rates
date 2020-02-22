package com.alexvit.revolutrates.currency

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("baseCurrency") val baseCurrencyCode: String,
    @SerializedName("rates") val currencyCodeToRate: Map<String, Double>
)