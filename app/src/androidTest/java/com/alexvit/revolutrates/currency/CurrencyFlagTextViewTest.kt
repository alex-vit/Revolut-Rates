package com.alexvit.revolutrates.currency

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CurrencyFlagTextViewTest {

    private lateinit var flagView: CurrencyFlagTextView

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        flagView = CurrencyFlagTextView(context)
    }

    @Test
    fun showsFlagForAllSupportedCurrencies() {
        Currency.SUPPORTED_CURRENCY_CODES.map(::CurrencyImpl)
            .forEach { currency ->
                flagView.showFlagFor(currency)
                assertNotNull(flagView.text)
            }
    }

    @Test
    fun isEmptyForUnsupportedCurrency() {
        val kwanza = "AOA"
        assertFalse(Currency.SUPPORTED_CURRENCY_CODES.contains(kwanza))
        flagView.showFlagFor(CurrencyImpl(kwanza))
        assertTrue(flagView.text.isNullOrBlank())
    }

}