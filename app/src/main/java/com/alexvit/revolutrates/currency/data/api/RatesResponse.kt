package com.alexvit.revolutrates.currency.data.api

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("baseCurrency") val baseCurrencyCode: String,
    @SerializedName("rates") val currencyCodeToRate: Map<String, Double>
)