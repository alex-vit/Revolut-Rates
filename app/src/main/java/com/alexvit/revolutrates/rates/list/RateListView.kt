package com.alexvit.revolutrates.rates.list

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.common.OpenForTests
import com.alexvit.revolutrates.common.UiEvent
import com.alexvit.revolutrates.common.UiView
import io.reactivex.Observer

@OpenForTests
class RateListView(container: View, override val eventConsumer: Observer<UiEvent>) : UiView,
    RateListener {

    data class ItemClicked(val item: RateItem) : UiEvent
    data class AmountChanged(val item: RateItem, val newAmount: Double) : UiEvent

    private val recyclerView: RecyclerView = container.findViewById(R.id.recycler_view)
    private val adapter = RateAdapter(this)

    init {
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = LinearLayoutManager(container.context)
        recyclerView.adapter = adapter
    }

    fun setItems(items: Map<String, RateItem>) {
        adapter.setItems(items)
    }

    fun show() {
        recyclerView.isVisible = true
    }

    fun hide() {
        recyclerView.isVisible = false
    }

    override fun onItemClicked(item: RateItem) {
        emit(ItemClicked(item))
        recyclerView.postDelayed({ recyclerView.scrollToPosition(0) }, 100)
    }

    override fun onAmountChanged(item: RateItem, newAmount: Double) {
        emit(AmountChanged(item, newAmount))
    }

}