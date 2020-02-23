package com.alexvit.revolutrates.rates.data

import com.alexvit.revolutrates.common.AppScope
import com.alexvit.revolutrates.rates.data.api.ApiRatesDataSource
import com.alexvit.revolutrates.rates.data.api.RatesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RatesRepositoryModule {

    @Provides
    @AppScope
    fun repository(source: ApiRatesDataSource): RatesRepository =
        RatesRepository(source)

    @Provides
    @AppScope
    fun apiSource(api: RatesApi): ApiRatesDataSource =
        ApiRatesDataSource(api)

    @Provides
    @AppScope
    fun ratesApi(retrofit: Retrofit): RatesApi = retrofit.create(
        RatesApi::class.java
    )

    @Provides
    @AppScope
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://hiring.revolut.codes/api/android/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}