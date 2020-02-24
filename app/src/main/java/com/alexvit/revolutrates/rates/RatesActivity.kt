package com.alexvit.revolutrates.rates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexvit.revolutrates.R
import com.alexvit.revolutrates.app.App
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.rates.di.DaggerRatesComponent
import com.alexvit.revolutrates.rates.list.RateAdapter
import com.alexvit.revolutrates.rates.list.RateItem
import com.alexvit.revolutrates.rates.list.RateListener
import io.reactivex.disposables.CompositeDisposable
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
    }

    override fun onResume() {
        super.onResume()
        subs.add(vm.getState().subscribe { state -> adapter.setItems(state.items) })
    }

    override fun onPause() {
        subs.clear()
        super.onPause()
    }

}
