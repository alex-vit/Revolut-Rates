package com.alexvit.revolutrates.rates.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.alexvit.revolutrates.common.ActivityScope
import com.alexvit.revolutrates.rates.RatesViewModel
import com.alexvit.revolutrates.rates.data.RatesRepository
import dagger.Module
import dagger.Provides

@Module
class RatesModule {

    @Provides
    @ActivityScope
    fun vm(owner: FragmentActivity, factory: RatesViewModel.Factory) =
        ViewModelProvider(owner, factory).get(RatesViewModel::class.java)

    @Provides
    @ActivityScope
    fun vmFactory(ratesRepository: RatesRepository) =
        RatesViewModel.Factory(ratesRepository)

}