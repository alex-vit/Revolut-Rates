package com.alexvit.revolutrates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExchangeRateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private fun inflateView(parent: ViewGroup): View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exchange_rate, parent, false)

        internal fun create(parent: ViewGroup): ExchangeRateHolder =
            ExchangeRateHolder(inflateView(parent))
    }

    private val flag: CurrencyFlagView = itemView.findViewById<CurrencyFlagTextView>(R.id.flag)
    private val code: TextView = itemView.findViewById(R.id.code)
    private val name: TextView = itemView.findViewById(R.id.name)

    fun bind(item: ExchangeRateItem) {
        flag.showFlagFor(item.currency)
        code.text = item.currency.code()
        name.text = item.currency.name()
    }

}
