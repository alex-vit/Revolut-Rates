package com.alexvit.revolutrates.common.errorview

interface ErrorView {
    interface Listener {
        fun onRetry()
    }

    fun show()
    fun hide()
    fun setErrorMessage(error: String)
}
