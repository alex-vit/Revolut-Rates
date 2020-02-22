package com.alexvit.revolutrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexvit.revolutrates.currency.CurrencyImpl
import com.alexvit.revolutrates.ratelist.ExchangeRateAdapter
import com.alexvit.revolutrates.ratelist.ExchangeRateItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val exchangeRateAdapter =
        ExchangeRateAdapter(object :
            ExchangeRateAdapter.Listener {
            override fun onItemSelected(item: ExchangeRateItem) {
                onItemClicked(item)
            }
        })

    private val items: MutableMap<String, ExchangeRateItem> = listOf(
        "sgd",
        "thb",
        "zar",
        "sek",
        "pln",
        "ron",
        "rub",
        "nok",
        "nzd",
        "PHP",
        "krw",
        "MXN",
        "MYR",
        "ils",
        "inr",
        "isk",
        "jpy",
        "HUF",
        "idr",
        "GBP",
        "HKD",
        "HRK",
        "EUR",
        "USD",
        "AUD",
        "bgn",
        "brl",
        "cad",
        "chf",
        "cny",
        "CZK",
        "DKK",
        "OHAI"
    )
        .map { code ->
            code to ExchangeRateItem(
                CurrencyImpl.from(code),
                5.0
            )
        }
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
