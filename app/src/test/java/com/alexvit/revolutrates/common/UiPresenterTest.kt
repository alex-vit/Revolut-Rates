package com.alexvit.revolutrates.common

import androidx.lifecycle.Lifecycle
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UiPresenterTest {

    private open class View(eventConsumer: Observer<UiEvent>) : UiView(eventConsumer) {
        internal open fun setInt(x: Int) {}
    }

    private class Presenter(view: View, parentState: Flowable<Int>, lifecycle: Lifecycle) :
        UiPresenter<Int, Int, View>(view, parentState, lifecycle) {
        override fun onState(newState: Int) {
            view.setInt(newState)
        }

        override fun stateTransformer(): FlowableTransformer<Int, Int> =
            FlowableTransformer { upstream -> upstream.map { it * 2 } }
    }

    @Mock
    private lateinit var view: View
    @Mock
    private lateinit var lifecycle: Lifecycle
    private val parentState: PublishSubject<Int> = PublishSubject.create()
    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        presenter = Presenter(view, parentState.toFlowable(LATEST), lifecycle)
    }

    @Test
    fun `observes lifecycle when created`() {
        verify(lifecycle).addObserver(presenter)
    }

    @Test(expected = IllegalStateException::class)
    fun `calling start twice is an error`() {
        presenter.start()
        presenter.start()
    }

    @Test
    fun `ignores state changes while not started`() {
        parentState.onNext(1)
        verify(view, never()).setInt(anyInt())
    }

    @Test
    fun `ignores state changes after stopped`() {
        presenter.start()
        presenter.stop()
        parentState.onNext(1)
        verify(view, never()).setInt(anyInt())
    }

    @Test
    fun `sets transformed state when started`() {
        presenter.start()
        parentState.onNext(1)
        verify(view).setInt(2)
    }

}