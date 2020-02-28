package com.alexvit.revolutrates.rates

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.app.App
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.common.UiEvent
import com.alexvit.revolutrates.rates.di.DaggerRatesComponent
import com.alexvit.revolutrates.rates.list.RateAdapter
import com.alexvit.revolutrates.rates.list.RateItem
import com.alexvit.revolutrates.rates.list.RateListener
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_rates.*
import javax.inject.Inject


class RatesActivity : AppCompatActivity() {

    private val adapter by lazy {
        RateAdapter(object : RateListener {
            override fun onItemClicked(item: RateItem) {
                vm.baseChanged(item)
                recycler_view.postDelayed({ recycler_view.scrollToPosition(0) }, 100)
            }

            override fun onAmountChanged(
                item: RateItem,
                newAmount: Double
            ) {
                vm.baseAmountChanged(newAmount)
            }
        })
    }
    private val subs = CompositeDisposable()
    @Inject
    lateinit var vm: RatesViewModel
    private val events: PublishSubject<UiEvent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        DaggerRatesComponent.builder()
            .appComponent(App.component)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)

        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        RatesErrorPresenter(
            RatesErrorView(root, events),
            vm.getState(), lifecycle
        )
    }

    override fun onResume() {
        super.onResume()
        subs.addAll(
            vm.getState().subscribe(::applyState),
            events.subscribe { event ->
                when (event) {
                    RatesErrorView.Clicked -> vm.retryClicked()
                }
            }
        )
    }

    override fun onPause() {
        subs.clear()
        super.onPause()
    }

    private fun applyState(newState: RatesState) {
        when (newState.error) {
            null -> showItems(newState.items)
            else -> recycler_view.visibility = GONE
        }
    }

    private fun showItems(items: Map<String, RateItem>) {
        recycler_view.visibility = VISIBLE
        adapter.setItems(items)
    }

}
