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
class LifecycleUiPresenterTest {

    private open class View(override val eventConsumer: Observer<UiEvent>) : UiView {
        override fun getView(): android.view.View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        internal open fun setInt(x: Int) {}
    }

    private class Presenter(
        override val view: View,
        override val parentState: Flowable<Int>,
        lifecycle: Lifecycle
    ) :
        LifecycleUiPresenter<View, Int, Int>(view, parentState, lifecycle) {
        override fun onState(state: Int) {
            view.setInt(state)
        }

        override fun getStateTransformer(): FlowableTransformer<Int, Int> =
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