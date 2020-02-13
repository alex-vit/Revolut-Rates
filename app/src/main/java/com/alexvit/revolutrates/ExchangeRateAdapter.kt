package com.alexvit.revolutrates

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ExchangeRateAdapter :
    ListAdapter<ExchangeRateItem, ExchangeRateHolder>(ExchangeRateItem.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateHolder =
        ExchangeRateHolder.create(parent)

    override fun onBindViewHolder(holder: ExchangeRateHolder, position: Int) {
        holder.bind(getItem(position))
    }
}