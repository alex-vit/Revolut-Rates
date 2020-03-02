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
    private val container: View,
    private val eventConsumer: Observer<UiEvent>
) : UiView {

    object Clicked : UiEvent

    private val label: TextView = container.findViewById(R.id.error_label)
    private val button: Button = container.findViewById(R.id.error_button)

    init {
        button.setOnClickListener { emit(Clicked) }
    }

    override fun getView(): View = container

    override fun getEventConsumer(): Observer<UiEvent> = eventConsumer

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