package com.alexvit.revolutrates.rates

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.common.errorview.ErrorView

class RatesErrorView(container: View, listener: ErrorView.Listener) : ErrorView {

    private val label: TextView = container.findViewById(R.id.error_label)
    private val button: Button = container.findViewById(R.id.error_button)

    init {
        button.setOnClickListener { listener.onRetry() }
    }

    override fun show() {
        label.visibility = View.VISIBLE
        button.visibility = View.VISIBLE
    }

    override fun hide() {
        label.visibility = View.GONE
        button.visibility = View.GONE
    }

    override fun setErrorMessage(error: String) {
        label.text = error
    }

}