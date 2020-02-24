package com.alexvit.revolutrates.rates.list

import com.alexvit.revolutrates.rates.list.AmountInputFilter.Companion.hasTwoDecimals
import com.alexvit.revolutrates.rates.list.AmountInputFilter.Companion.reachedMaxLength
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AmountInputFilterTest {

    @Test
    fun `two decimals matches`() {
        assertTrue(".00".hasTwoDecimals())
        assertTrue("00.00".hasTwoDecimals())
    }

    @Test
    fun `more or less than 2 decimals fail`() {
        assertFalse(".0".hasTwoDecimals())
        assertFalse(".000".hasTwoDecimals())
    }

    @Test
    fun `two digits after comma fails`() {
        assertFalse(",00".hasTwoDecimals())
    }

    @Test
    fun `comma grouped two decimal matches`() {
        assertTrue("1,000.00".hasTwoDecimals())
    }

    @Test
    fun `nine plus chars means max length reached`() {
        assertTrue(" ".repeat(9).reachedMaxLength())
        assertTrue(" ".repeat(20).reachedMaxLength())
    }

    @Test
    fun `less than nine chars means max length not reached`() {
        assertFalse(" ".repeat(8).reachedMaxLength())
    }

}