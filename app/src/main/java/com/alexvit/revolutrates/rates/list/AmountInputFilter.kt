package com.alexvit.revolutrates.rates.list

import android.text.InputFilter
import android.text.Spanned
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE

class AmountInputFilter : InputFilter {

    companion object {
        @VisibleForTesting(otherwise = PRIVATE)
        val ALLOW: CharSequence? = null
        @VisibleForTesting(otherwise = PRIVATE)
        const val DENY = ""
        @VisibleForTesting(otherwise = PRIVATE)
        const val MAX_LENGTH = 9

        private val twoDecimalsPattern = Regex(".*[.]{1}\\d{2}")

        @VisibleForTesting(otherwise = PRIVATE)
        fun CharSequence.hasTwoDecimals(): Boolean = matches(twoDecimalsPattern)

        @VisibleForTesting(otherwise = PRIVATE)
        fun CharSequence.reachedMaxLength() = length >= MAX_LENGTH
    }

    override fun filter(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        destination: Spanned?,
        p4: Int,
        p5: Int
    ): CharSequence? = filterDestinationOnly(destination)


    @VisibleForTesting(otherwise = PRIVATE)
    fun filterDestinationOnly(destination: Spanned?): CharSequence? = when {
        destination == null -> ALLOW
        destination.reachedMaxLength() || destination.hasTwoDecimals() -> DENY
        else -> ALLOW
    }
}