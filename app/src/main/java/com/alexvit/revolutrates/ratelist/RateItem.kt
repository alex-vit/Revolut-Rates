package com.alexvit.revolutrates.ratelist

import androidx.recyclerview.widget.DiffUtil
import com.alexvit.revolutrates.currency.Currency

data class RateItem(val currency: Currency, val amount: Double, val priority: Long = 0) {

    companion object {
        internal val DIFF_CALLBACK: DiffUtil.ItemCallback<RateItem> =
            object : DiffUtil.ItemCallback<RateItem>() {
                override fun areItemsTheSame(
                    oldItem: RateItem,
                    newItem: RateItem
                ): Boolean = oldItem.currency.code() == newItem.currency.code()

                override fun areContentsTheSame(
                    oldItem: RateItem,
                    newItem: RateItem
                ): Boolean = oldItem == newItem
            }
    }

}
