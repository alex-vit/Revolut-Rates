package com.alexvit.revolutrates.rates.list

import androidx.recyclerview.widget.RecyclerView
import com.alexvit.revolutrates.currency.Currency
import com.alexvit.revolutrates.currency.CurrencyImpl
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SortByPriorityThenNameCallbackTest {

    @Mock
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var sortCallback: SortByPriorityThenNameCallback

    @Before
    fun setUp() {
        sortCallback = SortByPriorityThenNameCallback(adapter)
    }

    @Test
    fun `same currency code means same item`() {
        val a = RateItem(CurrencyImpl("AUD"), 1.0, 0)
        val b = a.copy(amount = 2.0, priority = 100)
        assertTrue(sortCallback.areItemsTheSame(a, b))
    }

    @Test
    fun `sorts by priority desc`() {
        val higherPriority = RateItem(CurrencyImpl("USD"), 1.0, 1)
        val lowerPriority = RateItem(CurrencyImpl("AUD"), 1.0, 0)
        assertTrue(sortCallback.compare(higherPriority, lowerPriority) < 0)
        assertTrue(sortCallback.compare(lowerPriority, higherPriority) > 0)
    }

    @Test
    fun `works for min max priority values`() {
        val higherPriority = RateItem(Currency.default(), 1.0, Long.MAX_VALUE)
        val lowerPriority = RateItem(Currency.default(), 1.0, Long.MIN_VALUE)
        assertTrue(sortCallback.compare(higherPriority, lowerPriority) < 0)
        assertTrue(sortCallback.compare(lowerPriority, higherPriority) > 0)
    }

    @Test
    fun `sorts by name given same priority`() {
        val nameStartsWithA = RateItem(CurrencyImpl("AUD"), 1.0, 1)
        val nameStartsWithU = RateItem(CurrencyImpl("USD"), 1.0, 1)
        assertTrue(sortCallback.compare(nameStartsWithA, nameStartsWithU) < 0)
        assertTrue(sortCallback.compare(nameStartsWithU, nameStartsWithA) > 0)
    }

}