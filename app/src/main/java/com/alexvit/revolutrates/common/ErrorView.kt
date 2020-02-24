package com.alexvit.revolutrates.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import com.alexvit.revolutrates.R

class ErrorView(container: View, listener: Listener) {

    interface Listener {
        fun buttonClicked()
    }

    private val label: TextView = container.findViewById(R.id.error_label)
    private val button: Button = container.findViewById(R.id.error_button)

    init {
        button.setOnClickListener { listener.buttonClicked() }
    }

    fun showError(error: Throwable) {
        label.visibility = VISIBLE
        button.visibility = VISIBLE
        val errorText = listOfNotNull(error::class.java.simpleName, error.message)
            .joinToString(":\n")
        label.text = errorText
    }

    fun hide() {
        label.visibility = GONE
        button.visibility = GONE
    }

}