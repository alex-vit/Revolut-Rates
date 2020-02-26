package com.alexvit.revolutrates.rates.list

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback

class SortByPriorityThenNameCallback(adapter: RecyclerView.Adapter<*>) :
    SortedListAdapterCallback<RateItem>(adapter) {

    override fun areItemsTheSame(item1: RateItem, item2: RateItem): Boolean {
        return item1.currency.code() == item2.currency.code()
    }

    override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
        return oldItem == newItem
    }

    override fun compare(o1: RateItem, o2: RateItem): Int {
        val diff = o2.priority.compareTo(o1.priority)
        return if (diff != 0) {
            diff
        } else {
            o1.currency.code().compareTo(o2.currency.code())
        }
    }

}