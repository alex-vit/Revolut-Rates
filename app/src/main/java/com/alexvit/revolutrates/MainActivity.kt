package com.alexvit.revolutrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val exchangeRateAdapter = ExchangeRateAdapter(object :
        ExchangeRateAdapter.Listener {
        override fun onItemSelected(item: ExchangeRateItem) {
            onItemClicked(item)
        }
    })

    private val items: MutableMap<String, ExchangeRateItem> = listOf("EUR", "USD")
        .map { code -> code to ExchangeRateItem(Currency.from(code), 5.0) }
        .toMap()
        .toMutableMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = exchangeRateAdapter

        exchangeRateAdapter.submitList(items.values.toList())
    }

    private fun onItemClicked(item: ExchangeRateItem) {
        val newList = listOf(item) + items.values.filter { it != item }
        exchangeRateAdapter.submitList(newList)
    }

}
