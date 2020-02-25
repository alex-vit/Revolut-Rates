package com.alexvit.revolutrates.currency

import androidx.annotation.Size
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

data class CurrencyImpl @Throws(UnknownCurrencyException::class) constructor(@Size(value = 3) private val currencyIsoCode: String) :
    Currency {

    companion object {

        @Throws(UnknownCurrencyException::class)
        private fun getCurrencyOrThrow(@Size(value = 3) code: String): java.util.Currency {
            return try {
                java.util.Currency.getInstance(code.toUpperCase(Locale.getDefault()))!!
            } catch (cause: IllegalArgumentException) {
                throw UnknownCurrencyException("Could not get currency $code.", cause)
            } catch (cause: NullPointerException) {
                throw UnknownCurrencyException("Could not get currency $code.", cause)
            } catch (cause: NoClassDefFoundError) {
                throw UnknownCurrencyException("Could not get currency $code.", cause)
            }
        }

        private fun currencyFormatWithoutSymbol(javaCurrency: java.util.Currency): NumberFormat {
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.currency = javaCurrency
            (numberFormat as DecimalFormat).run {
                decimalFormatSymbols = decimalFormatSymbols.apply { currencySymbol = "" }
            }
            return numberFormat
        }
    }

    private val code: String = currencyIsoCode.toUpperCase(Locale.getDefault())
    private var javaCurrency: java.util.Currency =
        getCurrencyOrThrow(currencyIsoCode)
    private val numberFormat: NumberFormat =
        currencyFormatWithoutSymbol(
            javaCurrency
        )

    override fun name(): String = javaCurrency.displayName

    override fun code(): String = javaCurrency.currencyCode

    override fun format(amount: Double): String = numberFormat.format(amount)

}
