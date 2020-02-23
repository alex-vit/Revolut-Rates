package com.alexvit.revolutrates

data class MainState(
    val topCurrencyCodes: List<String> = listOf("EUR"),
    val baseAmount: Double = 1.0,
    val rates: Map<String, Double> = mapOf(),
    val error: Throwable? = null
)
