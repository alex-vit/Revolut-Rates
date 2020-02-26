package com.alexvit.revolutrates.rates.data.api

import com.alexvit.revolutrates.common.OpenForTests
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@OpenForTests
class ApiRatesDataSource(
    private val api: RatesApi,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val computationScheduler: Scheduler = Schedulers.computation()
) {

    @SchedulerSupport(SchedulerSupport.IO)
    fun latestRates(baseCurrencyCode: String): Flowable<RatesResponse> =
        Flowable.interval(0, 1, TimeUnit.SECONDS, computationScheduler)
            .flatMapSingle { api.latestRates(baseCurrencyCode).subscribeOn(ioScheduler) }
            .onBackpressureLatest()

}
