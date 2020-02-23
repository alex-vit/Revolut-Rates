package com.alexvit.revolutrates.rates.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.alexvit.revolutrates.currency.Currency

class RateAdapter(
    private val listener: RateListener
) : RecyclerView.Adapter<RateHolder>(),
    RateListener {

    private val items: SortedList<RateItem> =
        SortedList(RateItem::class.java, object : SortedListAdapterCallback<RateItem>(this) {
            override fun areItemsTheSame(item1: RateItem, item2: RateItem): Boolean {
                return item1.currency.code() == item2.currency.code()
            }

            override fun compare(o1: RateItem, o2: RateItem): Int {
                val diff = (o2.priority - o1.priority).toInt()
                return if (diff != 0) {
                    diff
                } else {
                    o1.currency.code().compareTo(o2.currency.code())
                }
            }

            override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
                return oldItem == newItem
            }

        })

    init {
        items.add(
            RateItem(
                Currency.default(),
                1.0,
                1
            )
        )
        items.addAll(Currency.allExceptDefault().map {
            RateItem(
                it,
                0.0
            )
        })
    }

    fun setRates(rates: Map<String, Double>, baseAmount: Double) {
        items.beginBatchedUpdates()
        for (i in 0 until items.size()) {
            val item = items[i]
            val rate = rates[item.currency.code()] ?: continue
            items.updateItemAt(i, item.copy(amount = rate * baseAmount))
        }
        items.endBatchedUpdates()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        return RateHolder.create(
            parent,
            this
        )
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        holder.bind(items[position], position == 0)
    }

    override fun onItemClicked(item: RateItem) {
        listener.onItemClicked(item)
        items.updateItemAt(items.indexOf(item), item.copy(priority = System.currentTimeMillis()))
    }

    override fun onAmountChanged(item: RateItem, newAmount: Double) {
        listener.onAmountChanged(item, newAmount)
    }
}