package com.alexvit.revolutrates.currency

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import com.alexvit.revolutrates.R

class CurrencyFlagTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr),
    CurrencyFlagView {

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        val CODE_TO_FLAG = mapOf(
            "AUD" to R.string.flag_aud,
            "BGN" to R.string.flag_bgn,
            "BRL" to R.string.flag_brl,
            "CAD" to R.string.flag_cad,
            "CHF" to R.string.flag_chf,
            "CNY" to R.string.flag_cny,
            "CZK" to R.string.flag_czk,
            "DKK" to R.string.flag_dkk,
            "EUR" to R.string.flag_eur,
            "GBP" to R.string.flag_gbp,
            "HKD" to R.string.flag_hkd,
            "HRK" to R.string.flag_hrk,
            "HUF" to R.string.flag_huf,
            "IDR" to R.string.flag_idr,
            "ILS" to R.string.flag_ils,
            "INR" to R.string.flag_inr,
            "ISK" to R.string.flag_isk,
            "JPY" to R.string.flag_jpy,
            "KRW" to R.string.flag_krw,
            "MXN" to R.string.flag_mxn,
            "MYR" to R.string.flag_myr,
            "NOK" to R.string.flag_nok,
            "NZD" to R.string.flag_nzd,
            "PHP" to R.string.flag_php,
            "PLN" to R.string.flag_pln,
            "RON" to R.string.flag_ron,
            "RUB" to R.string.flag_rub,
            "SEK" to R.string.flag_sek,
            "SGD" to R.string.flag_sgd,
            "THB" to R.string.flag_thb,
            "USD" to R.string.flag_usd,
            "ZAR" to R.string.flag_zar
        )
    }

    override fun showFlagFor(currency: Currency) {
        text = CODE_TO_FLAG[currency.code()]?.let(context::getString)
    }

}