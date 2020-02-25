package com.alexvit.revolutrates.common.errorview

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE

class ErrorPresenter(private val view: ErrorView) {

    companion object {
        @VisibleForTesting(otherwise = PRIVATE)
        fun formatError(error: Throwable): String =
            listOfNotNull(error::class.java.simpleName, error.message)
                .joinToString(":\n")
    }

    fun showError(error: Throwable) {
        view.show()
        view.setErrorMessage(formatError(error))
    }

    fun hide() {
        view.hide()
    }

}