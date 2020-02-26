package com.alexvit.revolutrates.rates.list

import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList

class RateAdapter(
    private val listener: RateListener
) : RecyclerView.Adapter<RateHolder>() {

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun upsert(sortedList: SortedList<RateItem>, items: Map<String, RateItem>) {
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
    }

    private val sortedList: SortedList<RateItem> =
        SortedList(RateItem::class.java, SortByPriorityThenNameCallback(this))

    fun setItems(items: Map<String, RateItem>) {
        upsert(sortedList, items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder =
        RateHolder.create(parent, listener)

    override fun getItemCount(): Int = sortedList.size()

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        holder.bind(sortedList[position], position == 0)
    }
}