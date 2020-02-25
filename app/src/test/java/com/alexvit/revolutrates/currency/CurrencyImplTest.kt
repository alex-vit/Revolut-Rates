package com.alexvit.revolutrates.currency

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyImplTest {

    @Test
    fun `works for all supported currency codes`() {
        Currency.SUPPORTED_CURRENCY_CODES.map(::CurrencyImpl)
    }

    @Test(expected = UnknownCurrencyException::class)
    fun `throws unknown currency exception on unknown code`() {
        CurrencyImpl("a strange currency code")
    }

    @Test
    fun `default is euro`() {
        assertEquals(Currency.default().code(), "EUR")
    }

    @Test
    fun `constructor is case insensitive`() {
        Currency.SUPPORTED_CURRENCY_CODES.map(String::toLowerCase).map(::CurrencyImpl)
    }

    @Test
    fun `formatted amount has only numbers, commas and dots`() {
        val numericCommaAndDot = Regex("^([0-9]|\\.|,)*\$")
        assertTrue(Currency.SUPPORTED_CURRENCY_CODES
            .map(::CurrencyImpl)
            .map { currency -> currency.format(5.0) }
            .all { amount -> amount.matches(numericCommaAndDot) })
    }

    @Test
    fun `unknown currency exception message indicates problematic code`() {
        val code = "random code"
        try {
            CurrencyImpl(code)
        } catch (e: UnknownCurrencyException) {
            assertTrue(e.message!!.contains(code))
        }
    }

}