package com.alexvit.revolutrates.common.errorview

import com.alexvit.revolutrates.common.errorview.ErrorPresenter.Companion.formatError
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ErrorPresenterTest {

    @Mock
    private lateinit var errorView: ErrorView
    private lateinit var presenter: ErrorPresenter

    @Before
    fun setUp() {
        presenter = ErrorPresenter(errorView)
    }

    @Test
    fun `formatted error shows class and message`() {
        assertEquals(formatError(Exception("Test")), "Exception:\nTest")
    }

    @Test
    fun `formatted error without message is just class name`() {
        assertEquals(formatError(Exception()), "Exception")
    }

    @Test
    fun `presenter hides view`() {
        presenter.hide()
        verify(errorView).hide()
    }

    @Test
    fun `presenter shows view when setting error`() {
        presenter.showError(Exception())
        verify(errorView).show()
    }

    @Test
    fun `presenter sets formatted error message`() {
        presenter.showError(Exception("Test"))
        verify(errorView).setErrorMessage(ErrorPresenter.formatError(Exception("Test")))
    }

}