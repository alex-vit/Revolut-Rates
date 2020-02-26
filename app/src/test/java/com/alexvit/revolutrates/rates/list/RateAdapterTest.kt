package com.alexvit.revolutrates.rates.list

import androidx.recyclerview.widget.SortedList
import com.alexvit.revolutrates.currency.CurrencyImpl
import com.alexvit.revolutrates.rates.list.RateAdapter.Companion.upsert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RateAdapterTest {

    private val item = RateItem(CurrencyImpl("EUR"), 1.0)
    @Mock
    private lateinit var sortedList: SortedList<RateItem>

    @Test
    fun `new item is added`() {
        upsert(sortedList, mapOf("EUR" to item))
        verify(sortedList).add(item)
    }

    @Test
    fun `existing item is updated`() {
        `when`(sortedList.size()).thenReturn(1)
        `when`(sortedList.get(0)).thenReturn(item)
        val updatedItem = item.copy(priority = 1)
        upsert(sortedList, mapOf("EUR" to updatedItem))
        verify(sortedList, never()).add(updatedItem)
        verify(sortedList).updateItemAt(0, updatedItem)
    }

}