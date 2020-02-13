package com.alexvit.revolutrates

import androidx.annotation.Size
import androidx.annotation.StringRes
import java.text.NumberFormat
import java.util.*

class CurrencyImpl(currencyIsoCode: String) : Currency {

    companion object {

        @Size(value = 3)
        private fun validCode(code: String): String {
            require(code.length == 3) { "Currency ISO code should be 3 letters. Got $code." }
            return code.toUpperCase(Locale.getDefault())
        }

        private fun getCurrencyOrThrow(@Size(value = 3) code: String): java.util.Currency {
            return try {
                java.util.Currency.getInstance(code)!!
            } catch (cause: NullPointerException) {
                throw IllegalArgumentException("Could not get currency $code.", cause)
            } catch (cause: NoClassDefFoundError) {
                throw IllegalArgumentException("Could not get currency $code.", cause)
            }
        }
    }

    @Size(value = 3)
    private val code: String = validCode(currencyIsoCode)
    private var javaCurrency: java.util.Currency = getCurrencyOrThrow(code)
    private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = this@CurrencyImpl.javaCurrency
    }

    override fun name(): String = javaCurrency.displayName

    override fun code(): String = javaCurrency.currencyCode

    override fun format(amount: Double): String = numberFormat.format(amount)

}
