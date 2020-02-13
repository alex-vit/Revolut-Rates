package com.alexvit.revolutrates.ratelist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ExchangeRateAdapter(private val listener: Listener) :
    ListAdapter<ExchangeRateItem, ExchangeRateHolder>(
        ExchangeRateItem.DIFF_CALLBACK
    ) {

    interface Listener {
        fun onItemSelected(item: ExchangeRateItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateHolder =
        ExchangeRateHolder.create(
            parent,
            listener
        )

    override fun onBindViewHolder(holder: ExchangeRateHolder, position: Int) {
        holder.bind(getItem(position))
    }
}