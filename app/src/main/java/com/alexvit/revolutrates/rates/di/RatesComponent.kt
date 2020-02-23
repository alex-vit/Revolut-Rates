package com.alexvit.revolutrates.rates.di

import com.alexvit.revolutrates.app.AppComponent
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.common.ActivityScope
import com.alexvit.revolutrates.rates.RatesActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, RatesModule::class]
)
interface RatesComponent {
    fun inject(ratesActivity: RatesActivity)
}