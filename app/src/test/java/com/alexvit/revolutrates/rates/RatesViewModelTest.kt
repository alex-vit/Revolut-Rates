package com.alexvit.revolutrates.rates

import com.alexvit.revolutrates.currency.CurrencyImpl
import com.alexvit.revolutrates.rates.RatesViewModel.Companion.updateItems
import com.alexvit.revolutrates.rates.data.RatesRepository
import com.alexvit.revolutrates.rates.data.api.RatesResponse
import com.alexvit.revolutrates.rates.list.RateItem
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RatesViewModelTest {

    private val amount = 1.0
    private val items = mapOf(
        "EUR" to RateItem(CurrencyImpl("EUR"), amount),
        "USD" to RateItem(CurrencyImpl("USD"), amount)
    )
    private val rateFlowableSubject: BehaviorSubject<Flowable<RatesResponse>> =
        BehaviorSubject.createDefault(Flowable.never())
    @Mock
    private lateinit var repository: RatesRepository
    private lateinit var viewModel: RatesViewModel

    @Before
    fun setUp() {
        rateFlowableSubject.onNext(Flowable.never())
        `when`(repository.latestRates("EUR")).thenReturn(rateFlowableSubject
            .toFlowable(BackpressureStrategy.LATEST)
            .switchMap { it }
        )
        `when`(repository.latestRates("USD")).thenReturn(
            Flowable.just(RatesResponse("USD", mapOf("EUR" to 1.0)))
        )
        viewModel = RatesViewModel(repository)
    }

    @Test
    fun `changing base amount updates all amounts`() {
        val rates = RatesResponse("EUR", mapOf("USD" to 1.0))
        val newItems = updateItems(items, rates, 2.0)
        newItems.values
            .map(RateItem::amount)
            .forEach { assertEquals(it, 2.0, .001) }
    }

    @Test
    fun `amount updated on new rate`() {
        val rates = RatesResponse("EUR", mapOf("USD" to 2.0))
        val newItems = updateItems(items, rates, amount)
        assertEquals(newItems.getValue("USD").amount, 2.0 * amount, .001)
    }

    @Test
    fun `new base from rates response bumps priority`() {
        val rates = RatesResponse("USD", mapOf("EUR" to 1.0))
        val newItems = updateItems(items, rates, amount)
        assertNotEquals(newItems.getValue("USD").priority, 0)
        assertEquals(
            newItems.values
                .maxBy { it.priority }!!
                .currency.code(),
            "USD"
        )
    }

    @Test
    fun `sets initial empty state`() {
        withStateTestSubscriber { assertValueSequence(listOf(RatesState())) }
    }

    @Test
    fun `sets error from repository`() {
        val exception = Exception("Test")
        rateFlowableSubject.onNext(Flowable.error(exception))
        withStateTestSubscriber {
            assertLastValue { it.error == exception }
        }
    }

    @Test
    fun `updates items on response`() {
        val response = RatesResponse("EUR", mapOf("USD" to 2.0))
        emitResponse(response)
        withStateTestSubscriber {
            assertLastValue {
                it.items.containsValue(RateItem(CurrencyImpl("USD"), 2.0))
            }
        }
    }

    @Test
    fun `updates items on base amount change`() {
        emitResponse(RatesResponse("EUR", mapOf("USD" to 2.0)))
        viewModel.baseAmountChanged(5.0)
        withStateTestSubscriber {
            assertLastValue {
                it.items.containsValue(RateItem(CurrencyImpl("EUR"), 5.0))
                it.items.containsValue(RateItem(CurrencyImpl("USD"), 10.0))
            }
        }
    }

    @Test
    fun `sets highest priority to new base currency`() {
        emitResponse(RatesResponse("EUR", mapOf("USD" to 2.0)))
        viewModel.baseChanged(RateItem(CurrencyImpl("USD"), 2.0))
        verify(repository).latestRates("USD")
        withStateTestSubscriber {
            assertLastValue { state ->
                state.items.values
                    .maxBy(RateItem::priority)!!
                    .currency.code() == "USD"
            }
        }
    }

    private fun withStateTestSubscriber(f: (TestSubscriber<RatesState>).() -> Unit) {
        val subscriber = viewModel.getState().test()
        subscriber.f()
        subscriber.dispose()
    }

    private fun <T> TestSubscriber<T>.assertLastValue(predicate: (T) -> Boolean) {
        assertValueAt(valueCount() - 1) { predicate.invoke(it) }
    }

    private fun emitResponse(response: RatesResponse) {
        rateFlowableSubject.onNext(Flowable.just(response).concatWith(Flowable.never()))
    }

}