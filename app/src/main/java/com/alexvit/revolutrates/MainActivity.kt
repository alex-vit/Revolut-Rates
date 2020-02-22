package com.alexvit.revolutrates

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexvit.revolutrates.currency.RatesApi
import com.alexvit.revolutrates.ratelist.ExchangeRateAdapter
import com.alexvit.revolutrates.ratelist.ExchangeRateItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://hiring.revolut.codes/api/android/")
            .build()
        val ratesApi = retrofit.create(RatesApi::class.java)
        ratesApi.latestRates("eur")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { r -> Log.d("WTF", "got rates ${r.currencyCodeToRate.size}") },
                { e -> Log.d("WTF", e::class.java.simpleName, e) }
            )
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
