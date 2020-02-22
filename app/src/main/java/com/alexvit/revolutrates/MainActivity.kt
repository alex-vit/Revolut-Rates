package com.alexvit.revolutrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexvit.revolutrates.ratelist.ExchangeRateAdapter
import com.alexvit.revolutrates.ratelist.ExchangeRateItem
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val exchangeRateAdapter =
        ExchangeRateAdapter(object :
            ExchangeRateAdapter.Listener {
            override fun onItemSelected(item: ExchangeRateItem) {
                vm.onItemClicked(item)
            }
        })
    private val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val subs = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = exchangeRateAdapter
    }

    override fun onResume() {
        super.onResume()
        subs.add(vm.getItems().subscribe(exchangeRateAdapter::submitList))
    }

    override fun onPause() {
        subs.clear()
        super.onPause()
    }
}
