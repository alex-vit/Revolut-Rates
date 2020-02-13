package com.alexvit.revolutrates

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class CurrencyFlagTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), CurrencyFlagView {

    companion object {
        private val CODE_TO_FLAG = mapOf(
            "EUR" to R.string.flag_eur
        )
    }

    override fun showFlagFor(currency: Currency) {
        text = CODE_TO_FLAG[currency.code()]?.let(context::getString)
    }

}