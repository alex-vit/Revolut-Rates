package com.alexvit.revolutrates.rates.error

import org.junit.Assert
import org.junit.Test

class RatesErrorPresenterFormatTest {

    @Test
    fun `formatted error is class name and message`() {
        val error = Exception("Test")
        val expected = "Exception:\nTest"
        val actual = RatesErrorPresenter.formatError(error)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `formatted error is just class name when no error message`() {
        val error = Exception()
        val expected = "Exception"
        val actual = RatesErrorPresenter.formatError(error)
        Assert.assertEquals(expected, actual)
    }

}