package com.alexvit.revolutrates.currency.data.api

import com.alexvit.revolutrates.currency.RatesApi
import com.alexvit.revolutrates.currency.RatesResponse
import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ApiRatesDataSource(private val api: RatesApi) {

    companion object {
        private fun ratesResponseToMap(ratesResponse: RatesResponse): Map<String, Double> =
            ratesResponse.currencyCodeToRate + (ratesResponse.baseCurrencyCode to 1.0)
    }

    @SchedulerSupport(SchedulerSupport.IO)
    fun latestRates(baseCurrencyCode: String): Flowable<Map<String, Double>> =
        Flowable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapSingle {
                api.latestRates(baseCurrencyCode).subscribeOn(Schedulers.io())
                    .map(::ratesResponseToMap)
            }
            .onBackpressureLatest()

}
