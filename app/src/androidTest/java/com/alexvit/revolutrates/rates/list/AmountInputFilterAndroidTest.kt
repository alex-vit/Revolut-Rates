package com.alexvit.revolutrates.rates.list

import android.text.SpannableString
import org.junit.Assert.assertEquals
import org.junit.Test

class AmountInputFilterAndroidTest {

    private val filter = AmountInputFilter()

    @Test
    fun denyAfterTwoDecimals() {
        assertEquals(
            filter.filterDestinationOnly(SpannableString.valueOf("1.23")),
            AmountInputFilter.DENY
        )
    }

    @Test
    fun denyWhenMaxLengthReached() {
        assertEquals(
            filter.filterDestinationOnly(SpannableString.valueOf("123456789")),
            AmountInputFilter.DENY
        )
    }

    @Test
    fun allowWhileLessThanTwoDecimals() {
        listOf("1", "1.", "1.0").forEach { input ->
            assertEquals(
                filter.filterDestinationOnly(SpannableString.valueOf(input)),
                AmountInputFilter.ALLOW
            )
        }
    }
}