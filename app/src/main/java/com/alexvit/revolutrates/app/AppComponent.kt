package com.alexvit.revolutrates.app

import com.alexvit.revolutrates.common.AppScope
import com.alexvit.revolutrates.currency.data.RatesRepository
import com.alexvit.revolutrates.currency.data.RatesRepositoryModule
import dagger.Component

@AppScope
@Component(modules = [RatesRepositoryModule::class])
interface AppComponent {
    fun ratesRepository(): RatesRepository
}