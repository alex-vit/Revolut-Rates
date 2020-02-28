package com.alexvit.revolutrates.rates

import androidx.lifecycle.Lifecycle
import com.alexvit.revolutrates.rates.RatesErrorPresenter.Companion.formatError
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RatesErrorPresenterTest {

    private val parentState: PublishSubject<RatesState> = PublishSubject.create()
    @Mock
    private lateinit var view: RatesErrorView
    @Mock
    private lateinit var lifecycle: Lifecycle
    private lateinit var presenter: RatesErrorPresenter

    @Before
    fun setUp() {
        presenter = RatesErrorPresenter(view, parentState.toFlowable(LATEST), lifecycle)
        presenter.start()
    }

    @After
    fun cleanUp() {
        presenter.stop()
    }

    @Test
    fun `calls hide when no error`() {
        parentState.onNext(RatesState())
        verify(view).hide()
    }

    @Test
    fun `calls show when showing error message`() {
        parentState.onNext(RatesState(error = Exception()))
        verify(view).show()
    }

    @Test
    fun `sets error message`() {
        val error = Exception()
        parentState.onNext(RatesState(error = error))
        verify(view).setErrorMessage(formatError(error))
    }
}