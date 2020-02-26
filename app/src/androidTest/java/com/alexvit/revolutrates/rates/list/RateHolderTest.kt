package com.alexvit.revolutrates.rates.list

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.platform.app.InstrumentationRegistry
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.currency.CurrencyFlagTextView
import com.alexvit.revolutrates.currency.CurrencyImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RateHolderTest {

    private val currency = CurrencyImpl("EUR")
    private val rateItem = RateItem(currency, 5.0)
    private lateinit var euroFlag: String
    private lateinit var flag: CurrencyFlagTextView
    private lateinit var code: TextView
    private lateinit var name: TextView
    private lateinit var amount: EditText
    @Mock
    private lateinit var listener: RateListener

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        euroFlag = context.getString(CurrencyFlagTextView.CODE_TO_FLAG.getValue("EUR"))
        val parent = ConstraintLayout(context)
        val holder = RateHolder.create(parent, listener)
        holder.bind(rateItem, false)
        holder.itemView.run {
            flag = findViewById(R.id.flag)
            code = findViewById(R.id.code)
            name = findViewById(R.id.name)
            amount = findViewById(R.id.amount)
        }
    }

    @Test
    fun setsFlag() {
        assertEquals(flag.text, euroFlag)
    }

    @Test
    fun setsCode() {
        assertEquals(code.text, currency.code())
    }

    @Test
    fun setsName() {
        assertEquals(name.text, currency.name())
    }

    @Test
    fun setsAmount() {
        assertEquals(amount.text.toString(), currency.format(rateItem.amount))
    }

}