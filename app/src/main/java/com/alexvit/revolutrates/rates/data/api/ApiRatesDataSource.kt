package com.alexvit.revolutrates.rates.data.api

import io.reactivex.Flowable
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ApiRatesDataSource(private val api: RatesApi) {

    @SchedulerSupport(SchedulerSupport.IO)
    fun latestRates(baseCurrencyCode: String): Flowable<Map<String, Double>> =
        Flowable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapSingle {
                api.latestRates(baseCurrencyCode).subscribeOn(Schedulers.io())
                    .map(RatesResponse::currencyCodeToRate)
            }
            .onBackpressureLatest()

}
