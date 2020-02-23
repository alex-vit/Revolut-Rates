package com.alexvit.revolutrates

import com.alexvit.revolutrates.app.AppComponent
import com.alexvit.revolutrates.common.ActivityModule
import com.alexvit.revolutrates.common.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class, MainModule::class]
)
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}