package com.alexvit.revolutrates

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.alexvit.revolutrates.common.ActivityScope
import com.alexvit.revolutrates.currency.data.RatesRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun vm(owner: FragmentActivity, factory: MainViewModel.Factory) =
        ViewModelProvider(owner, factory).get(MainViewModel::class.java)

    @Provides
    @ActivityScope
    fun vmFactory(ratesRepository: RatesRepository) = MainViewModel.Factory(ratesRepository)

}