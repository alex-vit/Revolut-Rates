package com.alexvit.revolutrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexvit.revolutrates.app.App
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.ratelist.RateAdapter
import com.alexvit.revolutrates.ratelist.RateItem
import com.alexvit.revolutrates.ratelist.RateListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        RateAdapter(object :
            RateListener {
            override fun onItemClicked(item: RateItem) {
                recycler_view.scrollToPosition(0)
                vm.itemClicked(item)
            }

            override fun onAmountChanged(item: RateItem, newAmount: Double) {
                vm.amountChanged(item, newAmount)
            }
        })
    }
    private val subs = CompositeDisposable()
    @Inject
    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainComponent.builder()
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
        subs.add(vm.getState().subscribe { state ->
            adapter.setRates(
                state.rates,
                state.baseAmount
            )
        })
    }

    override fun onPause() {
        subs.clear()
        super.onPause()
    }

}
