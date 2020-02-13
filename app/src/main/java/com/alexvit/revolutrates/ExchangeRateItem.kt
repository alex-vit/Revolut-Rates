package com.alexvit.revolutrates

import android.content.Context
import androidx.recyclerview.widget.DiffUtil

data class ExchangeRateItem(val currency: Currency, val amount: Double) {

    companion object {
        internal val DIFF_CALLBACK: DiffUtil.ItemCallback<ExchangeRateItem> =
            object : DiffUtil.ItemCallback<ExchangeRateItem>() {
                override fun areItemsTheSame(
                    oldItem: ExchangeRateItem,
                    newItem: ExchangeRateItem
                ): Boolean = oldItem.currency.code() == newItem.currency.code()

                override fun areContentsTheSame(
                    oldItem: ExchangeRateItem,
                    newItem: ExchangeRateItem
                ): Boolean = oldItem == newItem
            }
    }

}
