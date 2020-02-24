package com.alexvit.revolutrates.rates.data

import com.alexvit.revolutrates.rates.data.api.ApiRatesDataSource
import com.alexvit.revolutrates.rates.data.api.RatesResponse
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

class RatesRepository(private val apiSource: ApiRatesDataSource) {

    fun latestRates(baseCurrencyCode: String): Flowable<RatesResponse> =
        apiSource.latestRates(baseCurrencyCode).observeOn(AndroidSchedulers.mainThread())

}
