package com.alexvit.revolutrates.rates.list

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.currency.CurrencyFlagTextView
import com.alexvit.revolutrates.currency.CurrencyFlagView

class RateHolder(itemView: View, private val listener: RateListener) :
    RecyclerView.ViewHolder(itemView) {

    companion object {
        private fun inflateView(parent: ViewGroup): View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false)

        internal fun create(parent: ViewGroup, listener: RateListener): RateHolder =
            RateHolder(inflateView(parent), listener)
    }

    private val flag: CurrencyFlagView = itemView.findViewById<CurrencyFlagTextView>(R.id.flag)
    private val code: TextView = itemView.findViewById(R.id.code)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val amount: EditText = itemView.findViewById<EditText>(R.id.amount).apply {
        setOnFocusChangeListener { _, focused ->
            if (focused) {
                moveCursorToEnd()
            }
        }
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (hasFocus()) {
                    editable?.toString()?.toDoubleOrNull()?.let { newAmount ->
                        listener.onAmountChanged(item!!, newAmount)
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        filters = arrayOf(AmountInputFilter())
    }
    private val clickableHack: View = itemView.findViewById(R.id.clickable_hack)
    private var item: RateItem? = null

    init {
        itemView.setOnClickListener {
            listener.onItemClicked(item!!)
        }
        clickableHack.setOnClickListener { listener.onItemClicked(item!!) }
    }

    fun bind(item: RateItem, focus: Boolean) {
        if (focus) {
            amount.post {
                amount.focusAndShowKeyboard()
            }
        }
        flag.showFlagFor(item.currency)
        code.text = item.currency.code()
        name.text = item.currency.name()
        val formattedAmount = item.currency.format(item.amount)
        if (!amount.hasFocus()) {
            amount.setText(formattedAmount)
        }
        this.item = item
    }

    private fun EditText.moveCursorToEnd() {
        post { setSelection(text.length) }
    }

    private fun EditText.focusAndShowKeyboard() {
        requestFocus()
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

}
