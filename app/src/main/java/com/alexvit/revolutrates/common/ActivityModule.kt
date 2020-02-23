package com.alexvit.revolutrates.common

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity) {
    @Provides
    @ActivityScope
    fun activity() = activity
}