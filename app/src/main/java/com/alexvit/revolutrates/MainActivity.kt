package com.alexvit.revolutrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val exchangeRateAdapter = ExchangeRateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = exchangeRateAdapter

        exchangeRateAdapter.submitList(listOf("EUR", "USD")
            .map(Currency.Companion::from)
            .map { currency -> ExchangeRateItem(currency, 5.0) }
        )
    }

}
