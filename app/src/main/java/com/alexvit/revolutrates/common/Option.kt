package com.alexvit.revolutrates.common

sealed class Option<T : Any> {
    companion object {
        fun <T : Any> of(value: T?): Option<T> = value?.let(::Some) ?: None()
        fun <T : Any> none(): Option<T> = None()
    }

    data class Some<T : Any>(val value: T) : Option<T>()
    class None<T : Any> : Option<T>()
}