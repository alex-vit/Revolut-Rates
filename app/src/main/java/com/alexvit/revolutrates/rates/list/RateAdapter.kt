package com.alexvit.revolutrates.rates.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback

class RateAdapter(
    private val listener: RateListener
) : RecyclerView.Adapter<RateHolder>() {

    private val sortedList: SortedList<RateItem> =
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

    fun setItems(items: Map<String, RateItem>) {
        sortedList.beginBatchedUpdates()
        val newItems = items.toMutableMap()
        for (i in 0 until sortedList.size()) {
            val code = sortedList.get(i).currency.code()
            val newItem = newItems[code] ?: continue
            sortedList.updateItemAt(i, newItem)
            newItems.remove(code)
        }
        newItems.values.forEach { item -> sortedList.add(item) }
        sortedList.endBatchedUpdates()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder =
        RateHolder.create(parent, listener)

    override fun getItemCount(): Int = sortedList.size()

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        holder.bind(sortedList[position], position == 0)
    }
}