package com.alexvit.revolutrates.currency

interface Currency {

    fun name(): String

    fun code(): String

    fun format(amount: Double): String

}
