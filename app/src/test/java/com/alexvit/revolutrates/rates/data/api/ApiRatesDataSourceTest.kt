package com.alexvit.revolutrates.rates.data.api

import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ApiRatesDataSourceTest {

    private val scheduler = TestScheduler()
    private val response = RatesResponse("EUR", mapOf())
    @Mock
    private lateinit var api: RatesApi
    private lateinit var apiSource: ApiRatesDataSource

    @Before
    fun setUp() {
        `when`(api.latestRates("EUR")).thenReturn(Single.just(response))
        apiSource = ApiRatesDataSource(api, scheduler, scheduler)
    }

    @Test
    fun `api source emits at one second intervals`() {
        val subscriber = apiSource.latestRates("EUR").test()
        scheduler.triggerActions()
        subscriber.assertValue(response)
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        subscriber.assertValueSequence(listOf(response, response))
        subscriber.dispose()
    }

}