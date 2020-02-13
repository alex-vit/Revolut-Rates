package com.alexvit.revolutrates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExchangeRateHolder(itemView: View, private val listener: ExchangeRateAdapter.Listener) :
    RecyclerView.ViewHolder(itemView) {

    companion object {
        private fun inflateView(parent: ViewGroup): View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exchange_rate, parent, false)

        internal fun create(
            parent: ViewGroup,
            listener: ExchangeRateAdapter.Listener
        ): ExchangeRateHolder =
            ExchangeRateHolder(inflateView(parent), listener)
    }

    private val flag: CurrencyFlagView = itemView.findViewById<CurrencyFlagTextView>(R.id.flag)
    private val code: TextView = itemView.findViewById(R.id.code)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val amount: EditText = itemView.findViewById<EditText>(R.id.amount).apply {
        setOnFocusChangeListener { _, focused ->
            if (focused) {
                moveCursorToEnd()
                item?.let(listener::onItemSelected)
            }
        }
    }
    private var item: ExchangeRateItem? = null

    init {
        itemView.setOnClickListener { amount.requestFocus() }
    }

    fun bind(item: ExchangeRateItem) {
        this.item = item
        flag.showFlagFor(item.currency)
        code.text = item.currency.code()
        name.text = item.currency.name()
        amount.setText(item.currency.format(item.amount))
    }

    private fun EditText.moveCursorToEnd() {
        post { setSelection(text.length) }
    }

}
