package com.alexvit.revolutrates.rates.list

import androidx.lifecycle.Lifecycle
import com.alexvit.revolutrates.rates.RatesState
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RateListPresenterTest {

    private val ratesState: Subject<RatesState> = PublishSubject.create()
    private val lifecycle: Lifecycle = mock(Lifecycle::class.java)
    private val view: RateListView = mock(RateListView::class.java)
    private val presenter =
        RateListPresenter(view, ratesState.toFlowable(BackpressureStrategy.LATEST), lifecycle)

    @Before
    fun setUp() {
        presenter.start()
    }

    @Test
    fun `hides on error`() {
        ratesState.onNext(RatesState(error = Exception()))
        verify(view).hide()
    }

    @Test
    fun `hides when items empty`() {
        ratesState.onNext(RatesState(items = mapOf()))
        verify(view).hide()
    }

    @Test
    fun `shows when displaying items`() {
        ratesState.onNext(RatesState())
        verify(view).show()
    }

    @Test
    fun `passes items to view`() {
        val state = RatesState()
        ratesState.onNext(state)
        verify(view).setItems(state.items)
    }

}