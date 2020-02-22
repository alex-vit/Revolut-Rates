package com.alexvit.revolutrates.currency

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {
    @GET("latest")
    fun latestRates(@Query("base") baseCurrency: String): Single<RatesResponse>
}
