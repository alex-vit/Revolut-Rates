package com.alexvit.revolutrates.rates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.app.App
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.common.UiEvent
import com.alexvit.revolutrates.rates.di.DaggerRatesComponent
import com.alexvit.revolutrates.rates.error.RatesErrorPresenter
import com.alexvit.revolutrates.rates.error.RatesErrorView
import com.alexvit.revolutrates.rates.list.RateListPresenter
import com.alexvit.revolutrates.rates.list.RateListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_rates.*
import javax.inject.Inject


class RatesActivity : AppCompatActivity() {

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

        RatesErrorPresenter(
            RatesErrorView(
                root,
                events
            ), vm.getState(), lifecycle
        )
        RateListPresenter(RateListView(root, events), vm.getState(), lifecycle)
    }

    override fun onResume() {
        super.onResume()
        subs.add(
            events.subscribe { event ->
                when (event) {
                    is RateListView.ItemClicked -> vm.baseChanged(event.item)
                    is RateListView.AmountChanged -> vm.baseAmountChanged(event.newAmount)
                    RatesErrorView.Clicked -> vm.retryClicked()
                }
            }
        )
    }

    override fun onPause() {
        subs.clear()
        super.onPause()
    }

}
