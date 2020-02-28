package com.alexvit.revolutrates.rates

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.common.OpenForTests
import com.alexvit.revolutrates.common.UiEvent
import com.alexvit.revolutrates.common.UiView
import io.reactivex.Observer

@OpenForTests
class RatesErrorView(
    container: View,
    eventConsumer: Observer<UiEvent>
) : UiView(eventConsumer) {

    object Clicked : UiEvent

    private val label: TextView = container.findViewById(R.id.error_label)
    private val button: Button = container.findViewById(R.id.error_button)

    init {
        button.setOnClickListener { emit(Clicked) }
    }

    fun show() {
        label.visibility = View.VISIBLE
        button.visibility = View.VISIBLE
    }

    fun hide() {
        label.visibility = View.GONE
        button.visibility = View.GONE
    }

    fun setErrorMessage(errorMessage: String) {
        label.text = errorMessage
    }
}