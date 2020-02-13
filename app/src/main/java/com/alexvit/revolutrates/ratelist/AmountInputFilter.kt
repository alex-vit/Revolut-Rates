package com.alexvit.revolutrates.ratelist

import android.text.InputFilter
import android.text.Spanned

class AmountInputFilter : InputFilter {

    companion object {
        private val ALLOW = null
        private const val DENY = ""
        private val twoDecimalsPattern = Regex(".*[.,]{1}\\d{2}")

        private fun Spanned.hasTwoDecimals(): Boolean = matches(twoDecimalsPattern)

        private fun Spanned.isMaxLength() = length >= 9
    }

    override fun filter(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        destination: Spanned?,
        p4: Int,
        p5: Int
    ): CharSequence? = when {
        destination == null -> ALLOW
        destination.isMaxLength() || destination.hasTwoDecimals() -> DENY
        else -> ALLOW
    }
}